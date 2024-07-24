package com.purna.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.purna.model.Post;
import com.purna.service.PostService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@PostMapping("/createPost")
	public ResponseEntity<Post> createPost(
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("userId") Long userId,
			@RequestParam(value = "image", required = false) MultipartFile image
			) throws IOException{
		Post post = new Post();
		post.setUserId(userId);
		post.setTitle(title);
		post.setContent(content);
		
		if(image != null && !image.isEmpty()) {
			post.setImage(image.getBytes());
		}
		return ResponseEntity.ok(postService.savePost(post));
	}
	
	@GetMapping("/getAllPosts")
	public ResponseEntity<List<Post>> getAllPosts(){
		return ResponseEntity.ok(postService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Map<String,Object>> getPostById(@PathVariable Long id){
		Map<String, Object> map =  postService.findById(id);
		return ResponseEntity.ok().body(map);
	}
	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> deletePost(@PathVariable Long id){
//		Optional<Post> findById = postService.findById(id);
//		if(findById.isPresent()) {
//			postService.deletePost(id);
//		}else {
//			throw new RuntimeException("Post not found with given id :"+id);
//		}
//		return ResponseEntity.noContent().build();
//	}
	
	@PutMapping("/editPost")
	public ResponseEntity<Post> editPost(
			@RequestParam Long postId,
			@RequestParam(required = false) String title,
			@RequestParam(required = false) Long userId,
			@RequestParam(required = false) String content,
			@RequestParam(required = false) MultipartFile image) throws IOException{ 
		Post post = new Post();
		post.setTitle(title);
		post.setUserId(userId);
		post.setContent(content);
		return ResponseEntity.ok(postService.editPost(postId, post,image));
	}

}
