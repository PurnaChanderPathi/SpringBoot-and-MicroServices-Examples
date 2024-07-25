package com.purna.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam("role") String role,
			@RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto
			) throws IOException{
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setRole(role);
		
		return ResponseEntity.ok(userService.saveUser(user,profilePhoto));
	}

	@PostMapping("/addUser")
	public ResponseEntity<Map<String,Object>> saveUser(@RequestBody User user,MultipartFile profilePhoto) throws IOException {
		Map<String,Object> map = userService.saveUserDetails(user,profilePhoto);
	return ResponseEntity.ok().body(map);
	}



	@GetMapping("/all")
	public ResponseEntity<List<User>> getAllusers (){
		return ResponseEntity.ok(userService.findAllUsers());
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String email){
		Optional<User> user = userService.findByUsername(email);
		return user.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/getByEmail/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email){
	     User user = userService.findByEmail(email);
		return ResponseEntity.ok().body(user);
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
			@RequestParam(required = false) String username,
			@RequestParam(required = false) String password,
			@RequestParam(required = false) MultipartFile profilePhoto
			) throws Exception{
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		return ResponseEntity.ok(userService.updateuserDetails(userId, user, profilePhoto));		
}
	

	

}
