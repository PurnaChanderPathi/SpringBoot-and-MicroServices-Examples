package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

	
	private String employeeName;
	
	private String employeeDept;
	
	private String employeeStatus;
	
	private String employeeLocation;
}
