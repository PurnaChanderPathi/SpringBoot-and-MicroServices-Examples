package com.example.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Repository.EmployeeRepository;
import com.example.dto.EmployeeDto;
import com.example.model.EmployeeDetails;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public String saveEmployee(EmployeeDto employeeDto) {
		EmployeeDetails employeeDetails = new EmployeeDetails();
		BeanUtils.copyProperties(employeeDto, employeeDetails);
		employeeRepository.save(employeeDetails);
		return "Employees Data inserted";
	}

	@Override 
	public List<EmployeeDetails> getEmployees( String employeeName, String employeeDept, String employeeStatus,
			String employeeLocation) {
		return  employeeRepository.findEmployeesByCriteria(employeeName, employeeDept, employeeStatus, employeeLocation);
		
	}

	@Override
	public List<EmployeeDetails> searchEmployee(EmployeeDto employeeDto) {
		return employeeRepository.findEmployeesByCriteria(employeeDto.getEmployeeName(), employeeDto.getEmployeeDept(),
				employeeDto.getEmployeeStatus(), employeeDto.getEmployeeLocation());
	}
	
	
	
	

}
