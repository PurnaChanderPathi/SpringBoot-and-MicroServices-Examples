package com.example.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.dto.MessageDto;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
	
	@Autowired
	private MessageRepository messageRepository;
	
    public Message sendMessage(MessageDto messageDto) {
        Message message = new Message();
        message.setSenderId(messageDto.getSenderId());
        message.setReceiverId(messageDto.getReceiverId());
        message.setContent(messageDto.getContent());
        message.setTimestamp(LocalDateTime.now());
        return messageRepository.save(message);
    }

//    public List<Message> getMessagesBetweenUsers(String senderId, String receiverId) {
//        return messageRepository.findBySenderIdAndReceiverId(senderId, receiverId);
//    }

    public Map<String,Object> getUserMessages(String receiverId){
        Map<String,Object> response = new HashMap<>();
        List<Message> userMessages = messageRepository.findByReceiverId(receiverId);
        if(!userMessages.isEmpty()){
            response.put("status", HttpStatus.FOUND.value());
            response.put("message","user Messages found with receiverId :"+receiverId);
            response.put("userMessages",userMessages);
        }else{
            response.put("status",HttpStatus.NOT_FOUND.value());
            response.put("message","user Messages not found with receiverId :"+receiverId);
        }
        return response;
    }


    public Map<String,Object> getMessagesBetweenUsers(String senderId, String recevierId){
        Map<String,Object> response = new HashMap<>();
        List<Message> getMessagesBtwUsers = messageRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverId(senderId,recevierId,recevierId,senderId);
        if(!getMessagesBtwUsers.isEmpty()){
            response.put("status",HttpStatus.FOUND.value());
            response.put("message","Messages between Users found with UserId :"+senderId+" And UserId :"+recevierId);
            response.put("MessagesBetweenUsers",getMessagesBtwUsers);
        }else{
            response.put("status",HttpStatus.NOT_FOUND.value());
            response.put("message","Messages between Users not found with UserId :"+senderId+" And UserId :"+recevierId);
        }
        return response;
    }
}
