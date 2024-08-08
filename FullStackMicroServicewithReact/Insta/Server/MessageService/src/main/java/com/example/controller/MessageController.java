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

import com.example.dto.MessageDto;
import com.example.entity.Message;
import com.example.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	
	@PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody MessageDto messageDto) {
        Message message = messageService.sendMessage(messageDto);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @GetMapping("/between")
    public ResponseEntity<List<Message>> getMessagesBetweenUsers(
            @RequestParam String senderId,
            @RequestParam String receiverId) {
        List<Message> messages = messageService.getMessagesBetweenUsers(senderId, receiverId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Message>> getMessagesForUser(@RequestParam String receiverId) {
        List<Message> messages = messageService.getMessagesForUser(receiverId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

}
