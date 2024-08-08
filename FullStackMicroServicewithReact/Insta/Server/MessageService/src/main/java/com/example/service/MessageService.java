package com.example.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Message> getMessagesForUser(String receiverId) {
        return messageRepository.findByReceiverId(receiverId);
    }

    public List<Message> getMessagesBetweenUsers(String senderId, String receiverId){
        List<String> ids = Arrays.asList(senderId,receiverId);
        return messageRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverId("1","2","2","1");
    }

}
