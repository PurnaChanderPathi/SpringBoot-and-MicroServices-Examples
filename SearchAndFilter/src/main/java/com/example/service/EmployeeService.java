package com.example.service;

import java.util.List;

import com.example.dto.EmployeeDto;
import com.example.model.EmployeeDetails;

public interface EmployeeService {
	
	public String saveEmployee(EmployeeDto dto);
	
	public List<EmployeeDetails> getEmployees( String employeeName, String employeeDept, String employeeStatus, String employeeLocation);
	
	public List<EmployeeDetails> searchEmployee(EmployeeDto employeeDto);

}
