package com.example.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.dto.UserDto;
import com.example.model.UserDetails;
import com.example.reporitory.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repository;
	
	WebClient webClient = WebClient.create();
	
	private static final String restUrl = "http://localhost:9192/Aadhar/{id}";

	@Override
	public String createuser(UserDto userDto) {
		
		Long aadhrNumber = userDto.getAadharId();
		
		String aadharResponse  =
				              webClient.get()
				              .uri(restUrl,aadhrNumber)
				              .retrieve()
				              .bodyToMono(String.class)
				              .block();
		
		if(aadharResponse.equals("AndhraPradesh") || aadharResponse.equals("Telangana")) {
			
			UserDetails entity = new UserDetails();
			
			BeanUtils.copyProperties(userDto, entity);
			
			repository.save(entity);
			return "user Created Successfully..";
			
		} else {
			return  "User creation failed..";
		}

	}

}
