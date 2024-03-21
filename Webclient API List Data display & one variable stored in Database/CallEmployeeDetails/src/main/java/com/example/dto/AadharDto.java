package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AadharDto {
	
	private Long aadharId;
	
	private String name;
	
	private String state;

}
