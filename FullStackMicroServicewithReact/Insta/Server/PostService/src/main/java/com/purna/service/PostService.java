package com.purna.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import com.purna.model.Post;
import com.purna.repository.PostRepository;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@Slf4j
public class PostService {
	
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private WebClient.Builder webClientBuilder;
	
	Map<String,Object> map=new HashMap<>();
	public Post savePost(Post post) {
		return postRepository.save(post);
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
	
	public void deletePost(Long id) {
		 postRepository.deleteById(id);
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
				postget.setContent(post.getTitle());				
			}

			if(image != null && !image.isEmpty()) {
				postget.setImage(image.getBytes());			
			}			
			return postRepository.save(postget);
		}else {
			throw new RuntimeException("Post with given Id :"+postId+" not found");
		}
	}
}
