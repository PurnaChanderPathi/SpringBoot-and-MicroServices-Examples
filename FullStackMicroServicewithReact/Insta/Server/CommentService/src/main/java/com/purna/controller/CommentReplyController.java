package com.purna.controller;

import java.util.HashMap;
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

import com.purna.model.CommentReply;
import com.purna.service.CommentReplyService;

@RestController
@RequestMapping("/api/v1/commentsReply")
public class CommentReplyController {
	
	@Autowired
	private CommentReplyService commentReplyService;
	
	Map<String, Object> response = new HashMap<>();
	
	@PostMapping("/addCommentReply")
	public ResponseEntity<CommentReply> addCommentReply(@RequestBody CommentReply commentReply){
		return ResponseEntity.ok(commentReplyService.addComment(commentReply));
	}
	
	@GetMapping("/getCommentsReplyByPostId")
	public ResponseEntity<Map<String, Object>> getCommentReplysByPostId(@RequestParam Long postId){
		Map<String, Object> response = commentReplyService.getCommentReplyByPostId(postId);
		return ResponseEntity.ok().body(response);
	}


	@GetMapping("/getAllCommentReply/{commentId}")
	public ResponseEntity<Map<String,Object>> getCommentReplyByCommentId(@PathVariable Long commentId){
		Map<String,Object> response = commentReplyService.getCommentReplyByCommentId(commentId);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/getCommentReplyByCommentIdAndPostId")
	public ResponseEntity<Map<String,Object>> getCommentReplyByCommentIdAndPostId(@RequestParam Long commentId, @RequestParam Long postId){
		Map<String,Object> response = commentReplyService.getCommentReplyByCommentIdAndPostId(commentId, postId);
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("/{commentReplyId}")
	public ResponseEntity<Map<String,Object>> deleteCommentReply(@PathVariable Long commentReplyId){
		Map<String,Object> response = commentReplyService.deleteById(commentReplyId);
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("/deleteByCommentId/{commentId}")
	public ResponseEntity<Map<String,Object>> deleteByCommentId(@PathVariable Long commentId){
		Map<String,Object> response = commentReplyService.deleteByCommentId(commentId);
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("/deleteByPostId/{postId}")
	public ResponseEntity<Map<String,Object>> deleteByPostId(@PathVariable Long postId){
		Map<String,Object> response = commentReplyService.deleteByPostId(postId);
		return ResponseEntity.ok().body(response);
	}
	
//	@PutMapping("/editCommentReply/{commentReplyId}")
//	public ResponseEntity<CommentReply> editComment(@PathVariable Long commentReplyId, @RequestBody CommentReply commentReply){
//		return ResponseEntity.ok(commentReplyService.editCommentReply(commentReplyId, commentReply));
//	}

	@PutMapping("/editCommentReply/{commentReplyId}")
	public ResponseEntity<Map<String,Object>> editComment(@PathVariable Long commentReplyId, @RequestBody CommentReply commentReply){
		Map<String,Object> result = commentReplyService.editCommentReply(commentReplyId,commentReply);
		return ResponseEntity.ok().body(result);
	}

}
