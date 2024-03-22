package com.example.service;

import java.util.List;

import com.example.dto.CallmessageDto;

public interface CallmessageService {
	
	public String saveCallMessage(CallmessageDto callmessageDto);
	
	public String saveListMessages(List<CallmessageDto> callmessageDtos);

}
