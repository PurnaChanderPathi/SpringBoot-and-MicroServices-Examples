package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.dto.CallmessageDto;
import com.example.model.Callmessage;
import com.example.repository.CallmessageRepository;

@Service
public class CallmesssageServiceImpl implements CallmessageService {

	@Autowired
	private CallmessageRepository callmessageRepository;


	@Override
	public String saveCallMessage(CallmessageDto callmessageDto) {
		
		System.out.println("Post method executed success...");
		
		Callmessage callmessage = new Callmessage();
		
		BeanUtils.copyProperties(callmessageDto, callmessage);
		
		callmessageRepository.save(callmessage);
		
	     System.out.println(callmessage.toString());
		
		if(callmessage.getCaseId() != null) {
			return "Call Messages Created Successfully..!";
		}
		
		return "Call Messages Not Created Successfully..!";
	}


	@Override
	public String saveListMessages(List<CallmessageDto> callmessageDtos) {
		
		Callmessage c1 = new Callmessage();
		
		BeanUtils.copyProperties(callmessageDtos, c1);

		System.out.println("Post List method executed successfully..! ");
		
		//callmessageRepository.saveAll(c1); 
		return "List Data Saved";
			
		
	}



	
	
	
	
//	@Override
//	public String saveCallMessage(CallmessageDto callmessageDto) {
//		
//		List<Message> messageResponses = webClient.get().uri(restUrl).retrieve().bodyToFlux(Message.class)
//				.collectList().block();
//		
//		List<CallmessageDto> dtoResponses = messageResponses.stream().map(messageResponse -> 
//		new CallmessageDto(0l, messageResponse.getBusinessUnit(), messageResponse.getSystemId(), messageResponse.getUnit(), messageResponse.getCaseCreatedDate())).collect(Collectors.toList());		
//		
//		List<Callmessage> callmessageResponses = dtoResponses.stream().map(dtoResponse -> 
//		new Callmessage(0l, dtoResponse.getMessageId(), dtoResponse.getBusinessUnit(), dtoResponse.getSystemId(), dtoResponse.getUnit(), dtoResponse.getCaseCreatedDate())).collect(Collectors.toList());
//		
//		callmessageRepository.saveAll(callmessageResponses);
//		
//		return "List Data Inserted";
//	}

}
