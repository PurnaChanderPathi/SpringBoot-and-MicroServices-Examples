package com.example.service;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Repo.AadharRepository;
import com.example.dto.AadharDto;
import com.example.model.AadharDetails;

@Service
public class AadharService {

	@Autowired
	private AadharRepository aadharRepository;

	public AadharDetails saveAadhar(AadharDto aadharDto) {
		AadharDetails aadharDetails = new AadharDetails((long) 0, aadharDto.getAadharId(), aadharDto.getName(),
				aadharDto.getState());
		return aadharRepository.save(aadharDetails);

	}

	public String getState(Long id) {
      
	   Optional<AadharDetails> res = aadharRepository.findByAadharId(id);
	   
	   if(res.isPresent()) {
		 return  res.get().getState();
	   }
	   
	   return "Not Found..";
		
	}
	
	
	
	public AadharDetails getstatesBy(Long id){
		
		Optional<AadharDetails> res = aadharRepository.findByAadharId(id);
		
		if(res.isPresent()) {
			return res.get();
		}
		
		return null;
		
	}
	
	public List<AadharDetails> getAllEmployes(){
		return  aadharRepository.findAll();
		
		
	}

//	public AadharDetails findByAadharId(Long id) throws Exception {
//		
//		Optional<AadharDetails> findByAadharId = aadharRepository.findByAadharId(id);
//		
//		if(findByAadharId.isPresent()) {
//			return findByAadharId.get();
//		}
//		else {
//			throw new Exception("Aadhar with given id" + id + "is not found");
//		}		
//		
//	}
//	
//	
//	public String findByAadhar() {
//		
//	}
	
	

}
