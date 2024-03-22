package com.example.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {

	private Long messageId;
	
	private String businessUnit;
	
	private Long systemId;
	
	private String unit;
	
	private LocalDate caseCreatedDate;

}
