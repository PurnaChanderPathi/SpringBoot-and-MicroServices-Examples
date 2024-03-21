package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.UserDetails;
import com.example.service.UserSevice;

@RestController
public class UserController {
	
	@Autowired
	private UserSevice userSevice;

	
	@PostMapping("/saveUser")
	public ResponseEntity<String> saveUser(@RequestBody UserDetails userDetails)
	{
		userSevice.saveUser(userDetails);
		return ResponseEntity.ok("Data Saved");
	}

}
