package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CallmessageDto;
import com.example.model.Callmessage;
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
	public ResponseEntity<String> saveListMessages(@RequestBody List<CallmessageDto> callmessageDtos) {
		String response =  callmessageService.saveListMessages(callmessageDtos);
		return new ResponseEntity<String>(response,HttpStatus.CREATED);
		
	}
	
	@GetMapping("getMessage/{id}")
	public ResponseEntity<Callmessage> getMessageById(@PathVariable Long id){
		Callmessage response = callmessageService.findMessageById(id);
		return new ResponseEntity<Callmessage>(response,HttpStatus.OK);
	}
	
	@GetMapping("getListBySystemId/{id}")
	public ResponseEntity<List<Callmessage>> findBySystemId(@PathVariable Long id){
		List<Callmessage> responses = callmessageService.findBySystemId(id);
		return new ResponseEntity<List<Callmessage>>(responses,HttpStatus.OK);
	}

}
