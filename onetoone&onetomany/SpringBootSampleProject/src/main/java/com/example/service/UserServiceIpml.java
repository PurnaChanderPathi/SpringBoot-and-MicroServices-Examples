package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.UserDetails;
import com.example.repository.UserRepository;

@Service
public class UserServiceIpml implements UserSevice
{
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails saveUser(UserDetails userDetails) {
		return userRepository.save(userDetails);
	}

}
