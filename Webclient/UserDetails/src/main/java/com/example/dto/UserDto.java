package com.example.dto;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private Date dateofBirth;
	
	private Long mobileNumber;
	
	private String address;
	
	private Long aadharId;

}
