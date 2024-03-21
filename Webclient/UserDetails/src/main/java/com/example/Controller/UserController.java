package com.example.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserDto;
import com.example.service.UserService;

@RestController
@RequestMapping
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	public String get(@PathVariable Long id) {
		return userService.get(id);
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@RequestBody UserDto dto) {
		return userService.saveUser(dto);
	}
	

}
