package com.example.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.SignUpDto;
import com.example.entity.User;
import com.example.exception.UserAlreadyExistsException;
import com.example.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User saveUser(SignUpDto signUpDto) {
		User user = userRepository.findByUsername(signUpDto.getUsername());
		if(user==null) {
			if(signUpDto.getUsername() == "" || signUpDto.getPassword() == "" || signUpDto.getRole() == "") {
				throw new UserAlreadyExistsException("Empty username or password or role");
			}else {
				User user2=new User();
				user2.setUsername(signUpDto.getUsername());
				user2.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
				user2.setRole(signUpDto.getRole());
				return userRepository.save(user2);
			}

			}else {
				throw new UserAlreadyExistsException("User Already exist with username");
			}
		}			
}
