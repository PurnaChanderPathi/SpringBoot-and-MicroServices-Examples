package com.example.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
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


	/*@Override
	public String saveListMessages(List<CallmessageDto> callmessageDtos) {
		
		List<Callmessage> callmessages = callmessageDtos.stream().map(dto -> 
		{
			Callmessage callmessage = new Callmessage();
			callmessage.setCaseId(0l);
			callmessage.setBusinessUnit(dto.getBusinessUnit());
			callmessage.setSystemId(dto.getSystemId());
			callmessage.setUnit(dto.getUnit());
			callmessage.setCaseCreatedDate(dto.getCaseCreatedDate());
			
			return callmessage;
		}).collect(Collectors.toList());
		callmessageRepository.saveAll(callmessages);
		return "List Data Inserted...!!";
		
	}*/
	
	
	@Override
	public String saveListMessages(List<CallmessageDto> callmessageDtos) {
	    try {
	        List<Callmessage> callmessages = callmessageDtos.stream().map(dto -> {
	            Callmessage callmessage = new Callmessage();
	            callmessage.setCaseId(0l);
	            callmessage.setBusinessUnit(dto.getBusinessUnit());
	            callmessage.setSystemId(dto.getSystemId());
	            callmessage.setUnit(dto.getUnit());
	            callmessage.setCaseCreatedDate(dto.getCaseCreatedDate());
	            return callmessage;
	        }).collect(Collectors.toList());
	        callmessageRepository.saveAll(callmessages);
	        return "List Data Inserted...!!";
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception for debugging purposes
	        return "Failed to insert list data: " + e.getMessage();
	    }
	}


	@Override
	public Callmessage findMessageById(Long id) {
		
		 Optional<Callmessage> response = callmessageRepository.findById(id);
		 
		 if(response.isPresent()) {
			 return response.get();
		 }else {
			 return null;
		 }


		  
		
	}


	@Override
	public List<Callmessage> findBySystemId(Long id) {
		  List<Callmessage> response = callmessageRepository.findBySystemId(id);
		  return response;


	}



	
	
	
//	@Override
//	public String saveListMessages(List<CallmessageDto> callmessageDtos) {
//		
//		Callmessage c1 = new Callmessage();
//		
//		BeanUtils.copyProperties(callmessageDtos, c1);
//
//		System.out.println("Post List method executed successfully..! ");
//		
//		//callmessageRepository.saveAll(c1); 
//		return "List Data Saved";
//			
//		
//	}
	
	



	
	
	
	
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
