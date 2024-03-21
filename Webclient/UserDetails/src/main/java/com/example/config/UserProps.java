package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties
@Data
public class UserProps {
	
	@Value("${baseurl}")
	private String baseurl;


}
