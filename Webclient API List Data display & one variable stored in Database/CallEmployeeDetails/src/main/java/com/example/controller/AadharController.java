package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.service.AadharService;


@RestController
public class AadharController {
	
	@Autowired
	private AadharService aadharService;

//	@GetMapping("/AllEmployes")
//	public String getAllEmployes() {
//		return aadharService.getAllEmployes();
//	}
	
	@GetMapping("/AllEmployes")
	public void fetchDataAndPrint() {
		 aadharService.getEmployees();
	}
	
//	@GetMapping("/FetchData")
//	public void fetchData() {
//		aadharService.fetchData();
//	}


}
