package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendMailWOADto {

	private String username;
	private String password; 
	private String name;
	private String emails; 
	private String appraisalType; 
	private String appraisalScore; 
	private String dueDate;
	private String subject; 
}
