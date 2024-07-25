package com.purna.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import com.purna.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.purna.model.User;
import com.purna.repository.UserRepository;

@Service
@Slf4j
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	Map<String, Object> map = new HashMap<>();
	
	public User saveUser(User user,MultipartFile profilePhoto) throws IOException {
		if(profilePhoto != null && !profilePhoto.isEmpty()) {
			user.setProfilePhoto(profilePhoto.getBytes());
		}
		return userRepository.save(user);
	}

	public Map<String,Object> saveUserDetails(User user, MultipartFile profilePhoto) throws IOException {
		User saveUser = new User();
		saveUser.setUsername(user.getUsername());
		saveUser.setPassword(passwordEncoder.encode(user.getPassword()));
		saveUser.setRole(user.getRole());
		if(profilePhoto != null && !profilePhoto.isEmpty()){
			user.setProfilePhoto(profilePhoto.getBytes());
		}
		User savedUser = userRepository.save(saveUser);
		map.put("status", HttpStatus.OK.value());
		map.put("message","Saved Successfully");
		map.put("UserDetails",savedUser);
		return map;
	}
	
	public Optional<User>  findByUsername(String username) {
		return Optional.ofNullable(userRepository.findByUsername(username));
	}
	
	public User findByEmail(String email){
		return userRepository.findByUsername(email);
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

	public Map<String,Object> forgotPassword(String username){
		Optional<User> findByUsername = Optional.ofNullable(userRepository.findByUsername(username));
		if(findByUsername.isPresent()){
			String otp= new DecimalFormat("000000").format(new Random().nextInt(999999));
			log.info("otp"+otp);


		}
	}

}
