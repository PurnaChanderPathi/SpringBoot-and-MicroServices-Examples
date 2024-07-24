package com.purna.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.purna.model.User;
import com.purna.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User saveUser(User user,MultipartFile profilePhoto) throws IOException {
		if(profilePhoto != null && !profilePhoto.isEmpty()) {
			user.setProfilePhoto(profilePhoto.getBytes());
		}
		return userRepository.save(user);
	}
	
	public Optional<User>  findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public Optional<User> findByEmail(String email){
		return userRepository.findByEmail(email);
	}
	
	public List<User> findAllUsers(){
		return userRepository.findAll();
	}
	
	public void deleteUser(Long id) {
		 userRepository.deleteById(id);
	}
	
	public User updateProfilePhoto(Long userId, MultipartFile profilePhoto) throws Exception {
		Optional<User> userDetails = userRepository.findById(userId);
		if(userDetails.isPresent()) {
			User getUser = userDetails.get();
			getUser.setProfilePhoto(profilePhoto.getBytes());
			return userRepository.save(getUser);
		}else {
			throw new Exception("No User Found with userId : "+userId);
		}
	}
	
	public User updateuserDetails(Long userId, User user,MultipartFile profilePhoto) throws Exception {
		Optional<User> userDetails = userRepository.findById(userId);
		if(userDetails.isPresent()) {
			User getUser = userDetails.get();
			
			if(user.getUsername() != null && !user.getUsername().isEmpty()) {
				getUser.setUsername(user.getUsername());	
			}
			if(user.getEmail() != null && !user.getEmail().isEmpty()) {
				getUser.setEmail(user.getEmail());
			}
			if(user.getPassword() != null && !user.getPassword().isEmpty()) {
				getUser.setPassword(user.getPassword());	
			}

			if(profilePhoto != null && !profilePhoto.isEmpty()) {
				getUser.setProfilePhoto(profilePhoto.getBytes());
			}
			return userRepository.save(getUser);
		}else {
			throw new RuntimeException("No User Found with userId : "+userId);
		}
	}

}
