package com.example.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailSender {

	private String username;
	private String password; 
	private String name;
	private String emails; 
	private String appraisalType; 
	private String appraisalScore; 
	private String dueDate;
	private String subject; 
	private MultipartFile attachments;
}
