package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.dto.AadharDto;
import com.example.model.AadharDetails;
import com.example.service.AadharService;

@RestController
public class AadharController {
	
	@Autowired
	private AadharService aadharService;

	@PostMapping("/save")
	public String saveAadhar(@RequestBody AadharDto dto) {
		AadharDetails aadharDetails = aadharService.saveAadhar(dto);
		return aadharDetails.toString();
	}
	
	
	@GetMapping("/{id}")
	public String get(@PathVariable Long id) {
		return aadharService.getState(id);
	}
	
	@GetMapping("/getEmployees")
	public List<AadharDetails> getAllEmployees() {
		return aadharService.getAllEmployes();
	}

}
