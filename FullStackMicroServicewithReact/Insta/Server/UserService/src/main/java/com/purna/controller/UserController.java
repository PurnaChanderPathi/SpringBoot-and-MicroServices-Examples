package com.purna.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.purna.model.ForgotPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.purna.model.User;
import com.purna.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("role") String role,
			@RequestParam("username") String username,
			@RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto
			) throws IOException{
		User user = new User();
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole(role);
		
		return ResponseEntity.ok(userService.saveUser(user,profilePhoto));
	}


	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllusers (){
		return ResponseEntity.ok(userService.findAllUsers());
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username){
		Optional<User> user = userService.findByUsername(username);
		return user.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/findByEmailId/{email}")
	public ResponseEntity<User> findByEmail(@PathVariable String email){
		Optional<User> findByEmail = Optional.ofNullable(userService.findByEmail(email));
		return findByEmail.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
		
	}
	
	@DeleteMapping("/id")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();	
	}
	
	@PutMapping("/updateProfilePhoto")
	public ResponseEntity<User> updateProfilePhoto(
			@RequestParam("userId") Long userId,
			@RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto
			) throws Exception{
		
		return ResponseEntity.ok(userService.updateProfilePhoto(userId, profilePhoto));
		
	}
	
	@PutMapping("/updateUser")
	public ResponseEntity<User> updateUser(
			@RequestParam(required = false) Long userId,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String password,
			@RequestParam(required = false) String username,
			@RequestParam(required = false) MultipartFile profilePhoto
			) throws Exception{
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		return ResponseEntity.ok(userService.updateuserDetails(userId, user, profilePhoto));		
}

}
