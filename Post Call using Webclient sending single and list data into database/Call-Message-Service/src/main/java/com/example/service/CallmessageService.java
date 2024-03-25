package com.example.service;

import java.util.List;

import com.example.dto.CallmessageDto;
import com.example.model.Callmessage;

public interface CallmessageService {
	
	public String saveCallMessage(CallmessageDto callmessageDto);
	
	public String saveListMessages(List<CallmessageDto> callmessageDtos);
	
	public Callmessage findMessageById(Long id);
	
	 List<Callmessage> findBySystemId(Long id);

}
