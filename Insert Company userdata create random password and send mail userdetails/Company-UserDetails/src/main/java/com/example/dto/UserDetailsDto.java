package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {

	private String userName;
	
	private Long mobileNumber;
	
	private String email;
	
	private String address;
	
	private String status;
}
