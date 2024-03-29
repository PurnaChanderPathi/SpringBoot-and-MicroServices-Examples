package com.example.service;


import com.example.dto.UserDetailsDto;
import com.example.model.UserDetails;


public interface UserService {

	public UserDetails createUser(UserDetailsDto userDetailsDto);
	
    public byte[] getAllUsersAsPDF();
}
