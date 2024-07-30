package com.purna.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.purna.service.FollowService;

@RestController
@RequestMapping("/api/follow")
public class FollowController{
	
	    @Autowired
	    private FollowService followService;
	    
	    @PostMapping("/followUser")
	    public ResponseEntity<Map<String,Object>> followUser(@RequestParam String followerUsername, @RequestParam String followeeUsername){
	        return ResponseEntity.ok().body(followService.follerUser(followerUsername,followeeUsername));
	    }
	    
	    @PostMapping("/unfollow")
	    public ResponseEntity<Map<String,Object>> unfollow(@RequestParam String followerUsername, @RequestParam String followeeUsername){
	        return ResponseEntity.ok().body(followService.unfollowUser(followerUsername,followeeUsername));
	    }
}
