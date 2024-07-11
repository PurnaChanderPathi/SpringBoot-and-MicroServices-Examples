package com.example.service;

import java.util.Map;

import com.example.dto.SigninDto;
import com.example.entity.MailCredentials;

public interface UserMailService {
	
	Map<String, Object> createUser(SigninDto signinDto);
	
    Map<String, Object> saveMailCredentials(MailCredentials credentials);

    Map<String, Object> getAllMailCredentials();

}
