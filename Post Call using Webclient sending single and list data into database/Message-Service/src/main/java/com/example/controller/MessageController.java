package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.MessageDto;
import com.example.model.Message;
import com.example.service.MessageService;

@RestController
@RequestMapping("/Message")
public class MessageController {
	
	@Autowired
	private MessageService messageService;

	@PostMapping("/save")
	public Message saveMessage(@RequestBody MessageDto messageDto) {
		return messageService.saveMessage(messageDto);
	}
	
	@GetMapping("/getAllMessages/{id}")
	public ResponseEntity<String> getfindById(@PathVariable Long id) {
		 String res =  messageService.findById(id);
		 return new ResponseEntity<String>(res,HttpStatus.OK);
		
	}
	
	@GetMapping("/getAllMessages")
	public ResponseEntity<String> getfindById() {
		 String res =  messageService.getAllMessages();
		 return new ResponseEntity<String>(res,HttpStatus.OK);
		
	}
	


}
