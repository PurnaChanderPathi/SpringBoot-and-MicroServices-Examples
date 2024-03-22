package com.example.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import com.example.dto.CallmessageDto;
import com.example.dto.MessageDto;
import com.example.model.Callmessage;
import com.example.model.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {
	
	WebClient webClient = WebClient.create();
	
	private static final String resturl = "http://localhost:9193/CallMessage/save";

	@Autowired
	private MessageRepository messageRepository;
	
	@Override
	public Message saveMessage(MessageDto messageDto) {
		Message message = new Message();
		BeanUtils.copyProperties(messageDto, message);
		return messageRepository.save(message);
	}

	@Override
	public String getAllMessages() {
		List<Message> getAllmessages = messageRepository.findAll();
//		List<CallmessageDto> getCallmessageDtos = getAllmessages.stream().map(getAllmessage -> new CallmessageDto(0l, getAllmessage.getBusinessUnit(), getAllmessage.getSystemId(), getAllmessage.getUnit(), getAllmessage.getCaseCreatedDate())).collect(Collectors.toList());

//		CallmessageDto getCallmessageDto =  (CallmessageDto) getAllmessages.stream().map(getAllmessage -> new CallmessageDto(0l, getAllmessage.getBusinessUnit(), getAllmessage.getSystemId(), getAllmessage.getUnit(), getAllmessage.getCaseCreatedDate()));
//	    Callmessage callMessage = webClient.post().uri(resturl).body(BodyInserters.fromValue(getCallmessageDto)).retrieve().bodyToMono(Callmessage.class).block();

		//System.out.println(callMessage);
		
		String result  = null;
		
		for(Message message : getAllmessages) {
			CallmessageDto callMessage = new CallmessageDto(message.getCaseId(), message.getBusinessUnit(), message.getSystemId(), message.getUnit(), message.getCaseCreatedDate());
			result = test(callMessage);
		}
		
		return result;
	}
	
	
	public String test(CallmessageDto callmessageDto) {
		String callMessage = webClient.post().uri(resturl).body(BodyInserters.fromValue(callmessageDto)).retrieve().bodyToMono(String.class).block();
		return callMessage;
	}
	
	public String findById(Long id) {
		 Optional<Message> findByIds = messageRepository.findById(id);
		 if(findByIds.isPresent()) {			 
		 
			 Message message =  findByIds.get();			 
			 
			 CallmessageDto c1 = new CallmessageDto();
			 BeanUtils.copyProperties(message, c1);
			 
			 Stream<Object> callmessageDto = findByIds.stream().map(u -> new CallmessageDto());
			
			String response =  webClient.post().uri(resturl).body(BodyInserters.fromValue(c1)).retrieve().bodyToMono(String.class).block();
			return response; 
			
		 }		 
		 return "message not found with id: "+id;
	}
	

}
