package com.purna.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import com.purna.model.Post;
import com.purna.repository.PostRepository;

import reactor.core.publisher.Mono;

@Service
public class PostService {
	
	private static final Logger logger = LoggerFactory.getLogger(PostService.class);
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private WebClient webClient;
	
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
		 
		Mono<Object> commentResultMono=webClient.get().uri(url).retrieve().bodyToMono(Object.class);
		
		//Handling Asynchronous nature of WebClient
		Object commentResult = commentResultMono.block();
		
		logger.info("Comment Result: {}",commentResult);
		
		map.put("Status", HttpStatus.OK.value());
		map.put("message", "fetched Successfully");
		map.put("PostResult", postResult.get());
		map.put("commentResult", commentResult);
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
