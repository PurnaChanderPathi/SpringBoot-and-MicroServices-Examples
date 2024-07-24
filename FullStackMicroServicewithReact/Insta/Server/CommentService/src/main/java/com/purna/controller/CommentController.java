package com.purna.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId){
		List<Comment> getComments = commentService.getCommentsByPostId(postId);
		return ResponseEntity.ok(getComments);
	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable Long commentId){
		commentService.deleteComment(commentId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/editComment/{commentId}")
	public ResponseEntity<Comment> editComment(@PathVariable Long commentId, @RequestBody Comment comment){
		return ResponseEntity.ok(commentService.updateComment(commentId, comment));
	}

}
