package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.EmployeeDto;
import com.example.model.EmployeeDetails;
import com.example.service.EmployeeService;

@RestController
@RequestMapping("/Employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("/save")
	public String saveEmployee(@RequestBody EmployeeDto employeeDto) {
		return employeeService.saveEmployee(employeeDto);
		
	}
	
	@GetMapping(value =  "list")
	public ResponseEntity<?> list(
								  @RequestParam(value = "employeeName",required = false) String employeeName, 
								  @RequestParam(value = "employeeDept",required = false)String employeeDept,
								  @RequestParam(value = "employeeStatus",required = false)String employeeStatus,
								  @RequestParam(value = "employeeLocation",required = false)String employeeLocation){
		List<EmployeeDetails> employeeList = employeeService.getEmployees( employeeName, employeeDept, employeeStatus, employeeLocation);
		return new ResponseEntity<>(employeeList,HttpStatus.OK);
	}
	
	
	@PostMapping(value = "getList")
	public ResponseEntity<?> getList(
								  @RequestParam(value = "employeeName",required = false) String employeeName, 
								  @RequestParam(value = "employeeDept",required = false)String employeeDept,
								  @RequestParam(value = "employeeStatus",required = false)String employeeStatus,
								  @RequestParam(value = "employeeLocation",required = false)String employeeLocation){
		List<EmployeeDetails> employeeList = employeeService.getEmployees( employeeName, employeeDept, employeeStatus, employeeLocation);
		return new ResponseEntity<>(employeeList,HttpStatus.OK);
	}
	
	@PostMapping("/search")
	public List<EmployeeDetails> searchEmployees(@RequestBody EmployeeDto employeeDto){
		return employeeService.searchEmployee(employeeDto);
	}
	
	

}
