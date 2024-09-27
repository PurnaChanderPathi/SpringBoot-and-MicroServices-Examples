package com.purna.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import com.purna.model.Post;
import com.purna.repository.PostRepository;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Slf4j
public class PostService {
	
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private WebClient.Builder webClientBuilder;


	ObjectMapper objectMapper = new ObjectMapper();
	
	Map<String,Object> map=new HashMap<>();

	public Map<String, Object> savePost(Post post) {
		Map<String, Object> responseMap = new HashMap<>();

		// Save the post
		Post savedPost = postRepository.save(post);
		Long userId = savedPost.getUserId();
		log.info("userId: {}", userId);

		// Construct URL to fetch user details
		String userUrl = "http://localhost:9195/api/v1/users/findByUserId/" + userId;
		log.info("userUrl: {}", userUrl);

		// Fetch user details with WebClient (no token)
		Map<String, Object> userMap = fetchResponseWithWebClient(userUrl);

		// Handle case where user details could not be fetched
		if (userMap == null || userMap.isEmpty()) {
			log.error("User service is offline or an error occurred");
			responseMap.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
			responseMap.put("message", "User service is offline or an error occurred");
			return responseMap;
		}

		// Convert the fetched response into a User object
		User userDto = null;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			userDto = objectMapper.convertValue(userMap.get("User"), User.class);
		} catch (IllegalArgumentException e) {
			log.error("Error converting user response to User DTO", e);
			responseMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
			responseMap.put("message", "Error processing user information");
			return responseMap;
		}

		// Handle null or empty fields in userDto or savedPost
		String username = Optional.ofNullable(userDto.getUsername()).orElse("unknown");
		String title = Optional.ofNullable(savedPost.getTitle()).orElse("Untitled");

		log.info("username: {} userId: {} title: {}", username, userId, title);

		// Construct the notification URL
		String notificationUrl = String.format(
				"http://localhost:2020/api/v1/notifications/newPost?username=%s&userId=%d&postTitle=%s",
				username, userId, title
		);

		// Send notification
		Object notificationResult = sendNotification(notificationUrl, savedPost.getPostId());
		responseMap.put("status", HttpStatus.OK.value());
		responseMap.put("message", "Your Post is posted...!");
		responseMap.put("notificationResult", notificationResult);

		return responseMap;
	}

	// WebClient call without token
	public Map<String, Object> fetchResponseWithWebClient(String url) {
		WebClient client = WebClient.builder()
				.baseUrl(url)
				.build();

		String responseFinal = client.get()
				.uri(url)
				.exchange()
				.flatMap(clientResponse -> {
					if (clientResponse.statusCode().is5xxServerError()) {
						// Handling 5xx server errors
						return clientResponse.bodyToMono(String.class);
					} else {
						// Normal response handling
						return clientResponse.bodyToMono(String.class);
					}
				})
				.block(); // Blocking to get the response synchronously

		Map<String, Object> responseMap = new HashMap<>();
		try {
			// Parsing the JSON response into a HashMap
			ObjectMapper objectMapper = new ObjectMapper();
			responseMap = objectMapper.readValue(responseFinal, HashMap.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			// Handle parsing exception
		}

		return responseMap;
	}


	// WebClient call with authorization token
	public Map<String, Object> fetchResponseWithWebClient(String url, String token) {
		WebClient client = WebClient.builder()
				.baseUrl(url)
				.build();

		String responseFinal = client.get()
				.uri(url)
				.header("Authorization", token)
				.exchange()
				.flatMap(clientResponse -> {
					if (clientResponse.statusCode().is5xxServerError()) {
						// Handling 5xx server errors
						return clientResponse.bodyToMono(String.class);
					} else {
						// Normal response handling
						return clientResponse.bodyToMono(String.class);
					}
				})
				.block(); // Blocking to get the response synchronously

		Map<String, Object> responseMap = new HashMap<>();
		try {
			// Parsing the JSON response into a HashMap
			ObjectMapper objectMapper = new ObjectMapper();
			responseMap = objectMapper.readValue(responseFinal, HashMap.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			// Handle parsing exception
		}

		return responseMap;
	}


	private void handleUserFetchError(Exception e, Long userId, Map<String, Object> responseMap) {
		if (e instanceof WebClientResponseException) {
			log.error("Error Fetching UserDetails with UserId {}", userId, e);
		} else if (e.getCause() instanceof java.net.ConnectException) {
			log.error("Error Fetching UserDetails: Service offline", e);
		}
		responseMap.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
		responseMap.put("message", "User service is offline or an error occurred");
	}

	private Object sendNotification(String notificationUrl, Long postId) {
		try {
			return webClientBuilder.build().post()
					.uri(notificationUrl)
					.retrieve()
					.bodyToMono(Object.class)
					.block();
		} catch (WebClientResponseException e) {
			if (e.getCause() instanceof java.net.ConnectException) {
				log.error("Error Notifying Notification: Service offline", e);
				return "Notification service is offline";
			} else {
				log.error("Error Notifying Notification for postId {}", postId, e);
				return "Failed to notify";
			}
		}
	}

	public Map<String,Object> findById(Long id){
		Map<String,Object> map = new HashMap<>();
		Optional<Post> postResult=postRepository.findById(id);
		 
		 if(postResult.isPresent()) {
			 Object commentResult = null;
				 String url = "http://localhost:9197/api/v1/comments/getAllComments/" + id;
				 try {
					 commentResult = webClientBuilder.build().get()
							 .uri(url)
							 .retrieve()
							 .bodyToMono(Object.class)
							 .block();
				 } catch (WebClientResponseException e) {
					 log.error("Error fetching comments for post {}: {}", id, e.getMessage());
				 } catch (WebClientException e) {
					 log.error("Error fetching comments: Service is offline", e);
					 commentResult = "Comments service is offline";
				 } catch (Exception e) {
					 log.error("Unexpected error fetching comments for post {}: {}", id, e);
				 }
				 log.info("Comment Result: {}", commentResult);

			 Object CommentReplyResult = null;
			 String CommentReplyUrl = "http://localhost:9197/api/v1/commentsReply/getCommentsReplyByPostId?postId="+id;
			 try {
				 CommentReplyResult = webClientBuilder.build().get()
						 .uri(CommentReplyUrl)
						 .retrieve()
						 .bodyToMono(Object.class)
						 .block();
			 } catch (WebClientResponseException e) {
					log.error("Error fetching commentReply for post {}: {}", id, e.getMessage());
			 } catch (WebClientException e){
				 log.error("Error fetching commentReply: Service is offline", e);
				 CommentReplyResult = "CommentReply service is offline";
			 }catch (Exception e){
				 log.error("Unexpected error fetching comments for post {}: {}:", id, e);
			 }
			 log.info("CommentReply Result; {}",CommentReplyResult);

			 Long userId = postResult.get().getUserId();
			 Object likesResult = null;
			 try {
				 likesResult = webClientBuilder.build().get()
						 .uri(uriBuilder -> uriBuilder
								 .scheme("http")
								 .host("localhost")
								 .port(9198)
								 .path("/api/v1/likes/getLikesByUserIdAndPostId")
								 .queryParam("userId",userId)
								 .queryParam("postId",id)
								 .build())
						 .retrieve()
						 .bodyToMono(Object.class)
						 .block();
			 } catch (WebClientResponseException e){
				 log.error("Error fetching Likes for post {}: {}", id, e.getMessage());
			 }catch (WebClientException e){
				 log.error("Error fetching Likes: Service is offline", e);
				 likesResult = "Likes service is offline";
			 }catch (Exception e){
				 log.error("Unexpected error fetching Likes for post {}: {}:", id, e);
			 }

			 log.info("Likes Result: {}",likesResult);

			 map.put("Status", HttpStatus.OK.value());
			 map.put("message", "fetched Successfully");
			 map.put("PostResult", postResult.get());
			 map.put("commentResult", commentResult);
			 map.put("CommentReplyResult", CommentReplyResult);
			 map.put("likesResult",likesResult);
		 }else{
			 map.put("Status", HttpStatus.NOT_FOUND.value());
			 map.put("message", "Post with given id :"+id+" not found");
		 }
		 return map;
	}
	
	public List<Post> findAll(){
		return postRepository.findAll();

	}
	

	public Map<String, Object> deletePost(Long id) {
		Map<String, Object> map = new HashMap<>();

		Post getPostDetails = postRepository.findById(id).orElse(null);
		if (getPostDetails != null) {
			postRepository.deleteById(id);

			String commentUrl = "http://localhost:9197/api/v1/comments/deleteByPostId/" + id;
			Map<String, Object> response = new HashMap<>();
			Object commentResult = null;
			try {
				commentResult = webClientBuilder.build().delete()
						.uri(commentUrl)
						.retrieve()
						.bodyToMono(Object.class)
						.block();
			} catch (WebClientResponseException e) {
					log.error("Error Deleting Comment", e);
			}catch (WebClientException e){
				commentResult = "Comment service is offline";
				response.put("message", "Comment service is offline");
				response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
			}catch (Exception e){
				response.put("message", "Error Deleting Comment");
				response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
				log.error("Unexpected error Deleting comment for post {}: {}", id, e);
			}

			// Handle CommentReply Deletion
			String commentReplyUrl = "http://localhost:9197/api/v1/commentsReply/deleteByPostId/" + id;
			Object commentReplyResult = null;
			try {
				commentReplyResult = webClientBuilder.build().delete()
						.uri(commentReplyUrl)
						.retrieve()
						.bodyToMono(Object.class)  // Deserialize JSON response to Map
						.block();  // Blocking to get the response immediately
			} catch (WebClientResponseException e) {
					log.error("Error Deleting CommentReply", e);
				}catch (WebClientException e){
				commentReplyResult = "CommentReply Service is offline";
				response.put("message", "CommentReply service is offline");
				response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
			} catch (Exception e) {
				log.error("Unexpected error Deleting commentReply for post {}: {}", id, e);
			}

			// Handle Like Deletion
			String likeUrl = "http://localhost:9198/api/v1/likes/" + id;

			Object likeResult = null;
			try {
				likeResult = webClientBuilder.build().delete()
						.uri(likeUrl)
						.retrieve()
						.bodyToMono(Object.class)
						.block();
			} catch (WebClientResponseException e) {
				if (e.getCause() instanceof java.net.ConnectException) {
					log.error("Error Deleting Likes", e);

				} else {
					log.error("Error Deleting Likes with given PostId {}: {}", id, e);
					response.put("message", "Error Deleting Likes");
					response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
				}
			}catch (WebClientException e){
				likeResult = "Likes Service is offline";
				response.put("message", "Likes service is offline");
				response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
			}

			map.put("status", HttpStatus.OK.value());
			map.put("message", "Post with given PostId: " + id + " is deleted..!");
			map.put("CommentResult", commentResult);
			map.put("CommentReplyResult", commentReplyResult);
			map.put("likeResult", likeResult);

		} else {
			map.put("status", HttpStatus.NOT_FOUND.value());
			map.put("message", "Post with given PostId: " + id + " is Not Found");
		}

		return map;
	}

	public Map<String,Object> editPost(Long postId,  Post post, MultipartFile image) throws IOException {
		Map<String,Object> response = new HashMap<>();
		Optional<Post> findPost = postRepository.findById(postId);
		if(findPost.isPresent()) {
			Post postget = findPost.get();
			if(post.getUserId() != null) {
				postget.setUserId(post.getUserId());
			}
			if(post.getTitle() != null && !post.getTitle().isEmpty()) {
				postget.setTitle(post.getTitle());
			}

			if(post.getContent() != null && !post.getContent().isEmpty()) {
				postget.setContent(post.getContent());
			}

			if(image != null && !image.isEmpty()) {
				postget.setImage(image.getBytes());
			}
			 postRepository.save(postget);
			response.put("status",HttpStatus.OK.value());
			response.put("message","Post Updated Successfully..!");
		}else{
			response.put("status",HttpStatus.NOT_FOUND.value());
			response.put("message","Post not found with postId :"+postId);
		}
		return response;
	}

	public Map<String, Object> findPostByTitle(String query) {
		Map<String, Object> map = new HashMap<>(); // Initialize the map
		Optional<List<Post>> optionalPosts = postRepository.findByTitle(query); // Fetch the posts

		if (optionalPosts.isPresent() && !optionalPosts.get().isEmpty()) {
			map.put("status", HttpStatus.OK.value());
			map.put("message", "Post details found with given title: " + query);
			map.put("query", optionalPosts.get()); // Include the posts in the map
		} else {
			map.put("status", HttpStatus.NOT_FOUND.value());
			map.put("message", "Post details not found with given title: " + query);
		}

		return map;
	}



}
