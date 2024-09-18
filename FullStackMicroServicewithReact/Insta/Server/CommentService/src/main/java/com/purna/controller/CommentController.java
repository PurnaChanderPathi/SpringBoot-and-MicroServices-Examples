package com.purna.controller;

import java.util.List;
import java.util.Map;

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

import com.purna.model.Comment;
import com.purna.service.CommentService;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/addComment")
	public ResponseEntity<Comment> addComment(@RequestBody Comment comment){
		Comment addComment = commentService.addComment(comment);
		return ResponseEntity.ok(addComment);
	}


	@GetMapping("/getAllComments/{postId}")
	public ResponseEntity<Map<String,Object>> getCommentsByPostId(@PathVariable Long postId){
		Map<String,Object> map = commentService.getCommentsByPostId(postId);
		return ResponseEntity.ok().body(map);
	}
	
	@GetMapping("/getCommentByPostIdAndCommentId")
	public ResponseEntity<Map<String, Object>> getCommentByPostIdAndCommentId(@RequestParam Long postId,
			@RequestParam Long commentId){
		Map<String, Object> map = commentService.getCommentsByPostIdAndCommentId(postId,commentId);
		return ResponseEntity.ok().body(map);
	}
	
//	@DeleteMapping("/{commentId}")
//	public ResponseEntity<Void> deleteComment(@PathVariable Long commentId){
//		commentService.deleteComment(commentId);
//		return ResponseEntity.noContent().build();
//	}
	
//	@PutMapping("/editComment/{commentId}")
//	public ResponseEntity<Comment> editComment(@PathVariable Long commentId, @RequestBody Comment comment){
//		return ResponseEntity.ok(commentService.updateComment(commentId, comment));
//	}

	@PutMapping("/editComment/{commentId}")
	public ResponseEntity<Map<String,Object>> editComment(@PathVariable Long commentId, @RequestBody Comment comment){
		Map<String,Object> result = commentService.updateComment(commentId,comment);
		return ResponseEntity.ok().body(result);
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<Map<String,Object>> deleteComment(@PathVariable Long commentId){
		Map<String,Object> response = commentService.deleteComment(commentId);
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("/deleteByPostId/{postId}")
	public ResponseEntity<Map<String,Object>> deleteByPostId(@PathVariable Long postId){
		Map<String,Object> response = commentService.deleteCommentByPostId(postId);
		return ResponseEntity.ok().body(response);
	}

}
