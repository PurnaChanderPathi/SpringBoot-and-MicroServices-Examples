package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserDetailsDto;
import com.example.model.UserDetails;
import com.example.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	public ResponseEntity<UserDetails> createUser(@RequestBody UserDetailsDto userDetailsDto){
		UserDetails createdUser = userService.createUser(userDetailsDto);
		return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
		
	}

}
