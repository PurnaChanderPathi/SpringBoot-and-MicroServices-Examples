package com.purna.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.purna.service.NotificationService;

@RestController
@RequestMapping("api/v1/notifications")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	
	Map<String, Object> map = new HashMap<>();
	
	@PostMapping("/sendNotification")
	public ResponseEntity<String> sendNotification(@RequestParam String recipientUsername,
			@RequestParam Long recipientId,
			@RequestParam String message)
	{
		notificationService.sendNotification(recipientUsername, recipientId, message);
		return ResponseEntity.ok().body("Notification Send Successfully...!");
	}
	
	@PostMapping("/userRegistration")
	public ResponseEntity<Map<String, Object>> notifyUserRegistration(@RequestParam String username,
			@RequestParam Long userId){
		notificationService.notifyUserRegistration(username, userId);
		map.put("status", "success");
		map.put("message", "User registration notification sent.");
		return ResponseEntity.ok().body(map);
	}
	
	@PostMapping("/newPost")
	public ResponseEntity<Map<String, Object>> notifyNewPost(@RequestParam String username,
			@RequestParam Long userId,
			@RequestParam String postTitle){
		notificationService.notifyNewPost(username, userId, postTitle);
		map.put("status", HttpStatus.OK.value());
		map.put("messaage", "New Post Notification Sent...!");
		return ResponseEntity.ok().body(map);
	}
	
    
}
