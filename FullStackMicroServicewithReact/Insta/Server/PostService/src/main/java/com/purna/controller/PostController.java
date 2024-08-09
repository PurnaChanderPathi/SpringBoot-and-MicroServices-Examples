package com.purna.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.purna.model.Post;
import com.purna.service.PostService;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
	
	@Autowired
	private PostService postService;

	@PostMapping("/createPost")
	public ResponseEntity<Map<String,Object>> createPost(
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("userId") Long userId,
			@RequestParam(value = "image", required = false) MultipartFile image
	) throws IOException {
		Post post = new Post();
		post.setUserId(userId);
		post.setTitle(title);
		post.setContent(content);

		if(image != null && !image.isEmpty()) {
			post.setImage(image.getBytes());
		}
		Map<String,Object> savePost = postService.savePost(post);
		return ResponseEntity.ok().body(savePost);
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

	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String,Object>> deletePost(@PathVariable Long id){
		Map<String, Object> result = postService.deletePost(id);
		return ResponseEntity.ok().body(result);
	}
	
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

	@GetMapping("/getByTitle")
	public ResponseEntity<Map<String,Object>> findPostByTitle(@RequestParam String query){
		Map<String,Object> response = postService.findPostByTitle(query);
		return ResponseEntity.ok().body(response);
	}

}
