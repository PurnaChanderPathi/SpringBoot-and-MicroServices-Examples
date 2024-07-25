package com.purna.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.purna.model.Like;
import com.purna.service.LikeService;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {
	
	@Autowired
	private LikeService likeService;
	
	@PostMapping("/like")
	public ResponseEntity<Map<String,Object>> likePost(@RequestBody Like like){
		Map<String,Object> map=likeService.likePost(like.getUserId(), like.getPostId());
		return ResponseEntity.ok().body(map);
	}

	@GetMapping("/getLikesByUserIdAndPostId")
	public ResponseEntity<Map<String,Object>> getLikesByUserIdAndPostId(
			@RequestParam Long userId,
			@RequestParam Long postId
			){
		Map<String,Object> map = likeService.getLikesByUserIdAndPostId(userId,postId);
		return ResponseEntity.ok().body(map);
	}
	
//	@PostMapping("/unlike")
//	public ResponseEntity<String> unLikePost(@RequestParam Long userId,@RequestParam Long postId){
//		likeService.unlikePost(userId, postId);
//		return ResponseEntity.ok("Post ubliked Successfully");
//	}

}
