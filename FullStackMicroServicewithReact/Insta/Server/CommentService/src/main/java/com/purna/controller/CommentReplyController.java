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
	
	
	@GetMapping("/getAllCommentReplys/{commentId}")
	public ResponseEntity<List<CommentReply>> getCommentReplyByCommentId(@PathVariable Long commentId){
		List<CommentReply> getCommetRepls = commentReplyService.getCommentReplyByCommentId(commentId);
		return ResponseEntity.ok(getCommetRepls);
	}
	
	@GetMapping("/getCommentByCommentIdAndPostId")
	public ResponseEntity<List<CommentReply>> getCommentReplyByCommentIdAndPostId(@RequestParam Long commentId, @RequestParam Long postId){
		List<CommentReply> getCommentReplys = commentReplyService.getCommentReplyByCommentIdAndPostId(commentId, postId);
		return ResponseEntity.ok().body(getCommentReplys);
	}
	
	@DeleteMapping("/deleteCommentReply/{commentReplyId}")
	public ResponseEntity<Void> deleteCommentReply(@PathVariable Long commentReplyId){
		commentReplyService.deleteById(commentReplyId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/editCommentReply/{commentReplyId}")
	public ResponseEntity<CommentReply> editComment(@PathVariable Long commentReplyId, @RequestBody CommentReply commentReply){
		return ResponseEntity.ok(commentReplyService.editCommentReply(commentReplyId, commentReply));
	}

}
