package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CallmessageDto;
import com.example.service.CallmessageService;

@RestController
@RequestMapping("/CallMessage")
public class CallmessageController {
	
	@Autowired
	private CallmessageService callmessageService;
	
	@PostMapping("/save")
	public String saveCallMessages(@RequestBody CallmessageDto callmessageDto) {
		return callmessageService.saveCallMessage(callmessageDto);
	}
	
	@PostMapping("/SaveListMessages")
	public String saveListMessages(@RequestBody List<CallmessageDto> callmessageDtos) {
		callmessageService.saveListMessages(callmessageDtos);
		return "";
	}

}
