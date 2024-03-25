package com.example.service;

import java.util.List;

import com.example.dto.MessageDto;
import com.example.model.Message;

public interface MessageService {
	
	
	public Message saveMessage(MessageDto messageDto);
	
	public String getAllMessages();
	
	public String findById(Long id);
	
	public String saveListData();
	
	public Message getDataById(Long id);
	
	List<Message> findBySystemId(Long id);


}
