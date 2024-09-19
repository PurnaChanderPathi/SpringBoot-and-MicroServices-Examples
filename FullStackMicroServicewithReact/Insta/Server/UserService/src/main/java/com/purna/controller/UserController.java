package com.purna.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.purna.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.purna.model.User;
import com.purna.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<Map<String,Object>> registerUser(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("role") String role,
			@RequestParam("username") String username,
			@RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto
	) throws IOException {
		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole(role);

		Map<String,Object> result = userService.saveUser(user,profilePhoto);

		return ResponseEntity.ok().body(result);
	}


	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllusers (){
		return ResponseEntity.ok(userService.findAllUsers());
	}


	@GetMapping("/{username}")
	public ResponseEntity<Map<String,Object>> getUserByusername(@PathVariable String username){
		Map<String,Object> result = userService.findByUsername(username);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/findByUsername")
	public ResponseEntity<Map<String,Object>> getUserByUsername(@RequestParam String query){
			Map<String,Object> result = userService.findByUsername(query);
			return ResponseEntity.ok().body(result);
	}

	@GetMapping("/findByUserId/{userId}")
	public ResponseEntity<Map<String,Object>> findByUserId(@PathVariable Long userId){
		Map<String,Object> result = userService.findByUserId(userId);
		return ResponseEntity.ok().body(result);
	}

	@GetMapping("/findByEmailId/{emailId}")
	public ResponseEntity<Map<String,Object>> findByEmailId(@PathVariable String emailId){
		Map<String,Object> result = userService.findByEmailId(emailId);
		return ResponseEntity.ok().body(result);
	}

	@Transactional
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<Map<String,Object>> deleteUser(@PathVariable Long userId){
		Map<String,Object> result = userService.deleteUser(userId);
		return ResponseEntity.ok().body(result);
	}

	@PutMapping("/updateProfilePhoto")
	public ResponseEntity<Map<String,Object>> updateprofilePhoto(
			@RequestParam("userId") Long userId,
			@RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto
	) throws IOException {
		return ResponseEntity.ok(userService.updateProfilePhoto(userId,profilePhoto));
	}

	@PutMapping("/updateUser")
	public ResponseEntity<Map<String,Object>> updateUser(
			@RequestParam(required = false) Long userId,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String password,
			@RequestParam(required = false) String username,
			@RequestParam(required = false) MultipartFile profilePhoto
	) throws IOException {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		Map<String,Object> result = userService.updateUserDetails(userId,user,profilePhoto);
		return ResponseEntity.ok().body(result);
	}

}
