package com.purna.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import com.purna.model.Post;
import com.purna.repository.PostRepository;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriUtils;

@Service
@Slf4j
public class PostService {
	
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private WebClient.Builder webClientBuilder;
	
	Map<String,Object> map=new HashMap<>();

	public Map<String, Object> savePost(Post post) {
		Map<String, Object> responseMap = new HashMap<>();

		// Save the post
		Post savedPost = postRepository.save(post);

		// Construct URL to fetch user details
		String userUrl = "http://localhost:9195/api/v1/users/findByUserId/" + savedPost.getUserId();

		// Fetch user details
		UserDto userDto = null;
		try {
			userDto = webClientBuilder.build().get()
					.uri(userUrl)
					.retrieve()
					.bodyToMono(UserDto.class)
					.block();
		} catch (WebClientResponseException e) {
			if (e.getCause() instanceof java.net.ConnectException) {
				log.error("Error Fetching UserDetails", e);
				userDto = null;
			} else {
				log.error("Error Fetching UserDetails with UserId {}", savedPost.getUserId(), e);
			}
		}

		// Handle case where user details could not be fetched
		if (userDto == null) {
			log.error("User service is offline or an error occurred");
			responseMap.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
			responseMap.put("message", "User service is offline or an error occurred");
			return responseMap;
		}

		// Construct URL for sending notification
//		String notificationUrl = "http://localhost:2020/api/v1/notifications/newPost?username=" +
//				UriUtils.encode(userDto.getUsername(), StandardCharsets.UTF_8) +
//				"&userId=" + savedPost.getUserId() +
//				"&postTitle=" + UriUtils.encode(savedPost.getTitle(), StandardCharsets.UTF_8);
		String username = userDto.getUsername();
		Long userId = savedPost.getUserId();
		String title = savedPost.getTitle();
		log.info("username: {} userId: {} title: {}",username,userId,title);
		String notificationUrl = "http://localhost:2020/api/v1/notifications/newPost?username=" +
				username +
				"&userId=" + userId +
				"&postTitle=" + title;

		// Send notification
		Object notificationResult = null;
		try {
			notificationResult = webClientBuilder.build().post()
					.uri(notificationUrl)
					.retrieve()
					.bodyToMono(Object.class)
					.block();
		} catch (WebClientResponseException e) {
			if (e.getCause() instanceof java.net.ConnectException) {
				log.error("Error Notifying Notification", e);
				notificationResult = "Notification service is offline";
			} else {
				log.error("Error Notifying Notification postId {}", savedPost.getPostId(), e);
			}
		}

		// Prepare response
		responseMap.put("status", HttpStatus.OK.value());
		responseMap.put("message", "Your Post is posted...!");
		responseMap.put("UserDto",userDto);
		responseMap.put("notificationResult", notificationResult);

		return responseMap;
	}


	public Map<String,Object> findById(Long id){
		 Optional<Post> postResult=postRepository.findById(id);
		 
		 if(postResult.isEmpty()) {
			 map.put("Status", HttpStatus.NOT_FOUND.value());
			 map.put("message", "Post not found");
			 return map;
		 }
		String url = "http://localhost:9197/api/v1/comments/getAllComments/" + id;

//		 String responseFinal=webClient.get().uri(url).header("Authorization",token).exchange().flatMap(
//				 clientResponse -> {
//					 if(clientResponse.statusCode().is5xxServerError()){
//						 clientResponse.body((clientHttpResponse,context)->{
//							 return clientHttpResponse.getBody();
//						 });
//						 return clientResponse.bodyToMono(String.class);
//					 }else
//						 return clientResponse.bodyToMono(String.class);
//				 }
//		 ).block();
//
//		 Map<String,Object> webClientMap=new ObjectMapper().readTree(responseFinal,HashMap.class);



		 
		Object commentResult = null;
		try {
				commentResult = webClientBuilder.build().get()
						.uri(url)
						.retrieve()
						.bodyToMono(Object.class)
						.block();
		}catch (WebClientResponseException e){
			if(e.getCause() instanceof java.net.ConnectException){
					log.error("Error fetching comments: Service is offline", e);
					commentResult = "Comments service is offline";
			}else{
				log.error("Error fetching comments for post {}: {}", id, e);  
			}

		}
		log.info("Comment Result: {}",commentResult);


		Object CommentReplyResult = null;
		try {
			String CommentReplyUrl = "http://localhost:9197/api/v1/commentsReply/getCommentsReplyByPostId?postId="+id;
			CommentReplyResult = webClientBuilder.build().get()
					.uri(CommentReplyUrl)
					.retrieve()
					.bodyToMono(Object.class)
					.block();	
		} catch (WebClientResponseException e) {
			if(e.getCause() instanceof java.net.ConnectException) {
				log.error("Error fetching commentReplys: Service is offline",e);
				CommentReplyResult = "CommentReplys service is offline";
			}else {
				log.error("Error fetching comments for post {}: {}",id,e);
			}
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
			if(e.getCause() instanceof java.net.ConnectException){
				log.error("Error fetching likes: Service is offline", e);
				likesResult = "Likes service is offline";
			}else{
				log.error("Error fetching likes for user {} and post {}: {}", userId, id, e);
			}
		}

		log.info("Likes Result: {}",likesResult);
		
		map.put("Status", HttpStatus.OK.value());
		map.put("message", "fetched Successfully");
		map.put("PostResult", postResult.get());
		map.put("commentResult", commentResult);
		map.put("CommentReplyResult", CommentReplyResult);
		map.put("likesResult",likesResult);
		 return map;
	}
	
	public List<Post> findAll(){
		return postRepository.findAll();

	}
	
	//public void deletePost(Long id) {
	//	 postRepository.deleteById(id);
	//}

	public Map<String, Object> deletePost(Long id) {
		Map<String, Object> map = new HashMap<>();

		Post getPostDetails = postRepository.findById(id).orElse(null);
		if (getPostDetails != null) {
			postRepository.deleteById(id);

			// Handle Comment Deletion
			String commentUrl = "http://localhost:9197/api/v1/comments/deleteByPostId/" + id;
			Map<String, Object> commentResult = new HashMap<>();
			try {
				commentResult = webClientBuilder.build().delete()
						.uri(commentUrl)
						.retrieve()
						.bodyToMono(Map.class)  // Deserialize JSON response to Map
						.block();  // Blocking to get the response immediately
			} catch (WebClientResponseException e) {
				if (e.getCause() instanceof java.net.ConnectException) {
					log.error("Error Deleting Comment", e);
					commentResult.put("message", "Comment service is offline");
					commentResult.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
				} else {
					log.error("Error Deleting Comment with postId {}: {}", id, e);
					commentResult.put("message", "Error Deleting Comment");
					commentResult.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
				}
			}

			// Handle CommentReply Deletion
			String commentReplyUrl = "http://localhost:9197/api/v1/commentsReply/deleteByPostId/" + id;
			Map<String, Object> commentReplyResult = new HashMap<>();
			try {
				commentReplyResult = webClientBuilder.build().delete()
						.uri(commentReplyUrl)
						.retrieve()
						.bodyToMono(Map.class)  // Deserialize JSON response to Map
						.block();  // Blocking to get the response immediately
			} catch (WebClientResponseException e) {
				if (e.getCause() instanceof java.net.ConnectException) {
					log.error("Error Deleting CommentReply", e);
					commentReplyResult.put("message", "CommentReply service is offline");
					commentReplyResult.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
				} else {
					log.error("Error Deleting CommentReply with PostId {}: {}", id, e);
					commentReplyResult.put("message", "Error Deleting CommentReply");
					commentReplyResult.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
				}
			}

			// Handle Like Deletion
			String likeUrl = "http://localhost:9198/api/v1/likes/" + id;
			Map<String, Object> likeResult = new HashMap<>();
			try {
				likeResult = webClientBuilder.build().delete()
						.uri(likeUrl)
						.retrieve()
						.bodyToMono(Map.class)  // Deserialize JSON response to Map
						.block();  // Blocking to get the response immediately
			} catch (WebClientResponseException e) {
				if (e.getCause() instanceof java.net.ConnectException) {
					log.error("Error Deleting Likes", e);
					likeResult.put("message", "Likes service is offline");
					likeResult.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
				} else {
					log.error("Error Deleting Likes with given PostId {}: {}", id, e);
					likeResult.put("message", "Error Deleting Likes");
					likeResult.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
				}
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


	public Post editPost(Long postId,  Post post, MultipartFile image) throws IOException {
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
			return postRepository.save(postget);
		}else {
			throw new RuntimeException("Post with given Id :"+postId+" not found");
		}
	}

	public Map<String,Object> findByTitle(String title){
		List<Post> PostResults = postRepository.findByTitle(title);
		if(PostResults==null){
			map.put("status",HttpStatus.NOT_FOUND.value());
			map.put("message","Post Details with given Title: "+title+" not Found");
		}else{
			map.put("status",HttpStatus.OK.value());
			map.put("message","Post Details found with given Title: "+title);
			map.put("PostResults",PostResults);
		}
		return map;
	}
}
