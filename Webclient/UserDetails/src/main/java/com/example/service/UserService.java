package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.config.UserProps;
import com.example.dto.UserDto;
import com.example.model.UserDetails;
import com.example.reporitory.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserProps props;
	
	@Autowired
	private WebClient webClient;
	

	public UserService(UserProps props, WebClient webClient) {
		super();
		this.props = props;
		this.webClient = WebClient.create(props.getBaseurl());
	}
	
	public String get(Long id) {
		return  webClient.get().uri(props.getBaseurl()+"/{id}",id).retrieve().bodyToMono(String.class).block();				
	}
	
	public String saveUser(UserDto dto) {
		
		UserDetails userDetails = new UserDetails((long)0, dto.getFirstName(), dto.getLastName(),
				dto.getEmail(), dto.getDateofBirth(), dto.getMobileNumber(), dto.getAddress(), dto.getAadharId());
		
		String AadharState =  get(dto.getAadharId());
		System.out.println(AadharState);
		
		if(AadharState.equals("Telangana") || AadharState.equals("AndhraPradesh"))
		{
			 repository.save(userDetails);
			 return "Data Inserted!!";
		}
		return "Not belong to TG & AP";
		
	}	
}
