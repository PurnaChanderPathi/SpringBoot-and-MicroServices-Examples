package com.purna.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.purna.model.CachingEn;
import com.purna.repository.CachingRepo;

@Service
public class CachingService {
	
	@Autowired
	private CachingRepo cachingRepo;
	
	Map<String, Object> response = new HashMap<>();
	
	@CachePut(value = "cachingEns",key = "#cachingEn.cachId")
	public CachingEn saveCh(CachingEn cachingEn) {
		return cachingRepo.save(cachingEn);	
	}
	
	@Cacheable("cachingEns")
	public Optional<CachingEn> findByCachId(Long cachId) {
	
		return cachingRepo.findById(cachId);
	}
	
	@Cacheable(value = "cachingEns", key = "#name")
	public CachingEn findByName(String name) {
		return cachingRepo.findByName(name);
	}
	
	@Cacheable(value = "cachingEns", key = "#email")
	public CachingEn findByEmail(String email) {
		return cachingRepo.findByEmail(email);
	}
	
	@CacheEvict(value = "cachingEns", key = "#cachId")
	public void deleteCache(Long cachId) {
		cachingRepo.deleteById(cachId);
	}
	
	@CachePut(value = "cachingEns", key = "#cachId")
	public Map<String, Object> updateEn(Long cachId, CachingEn cachingEn) {
		Optional<CachingEn> findById = cachingRepo.findById(cachId);
		if(findById.isPresent()) {
			CachingEn existingDetails = findById.get();
			if(cachingEn.getName() != null && !cachingEn.getName().isEmpty()) {
				existingDetails.setName(cachingEn.getName());
			}
			if(cachingEn.getEmail() != null && !cachingEn.getEmail().isEmpty()) {
				existingDetails.setEmail(cachingEn.getEmail());
			}
			if(cachingEn.getAddress() != null && !cachingEn.getAddress().isEmpty()) {
				existingDetails.setAddress(cachingEn.getAddress());
			}
			if(cachingEn.getMobile() != null) {
				existingDetails.setMobile(cachingEn.getMobile());
			}
			if(cachingEn.getCity() != null && !cachingEn.getCity().isEmpty()) {
				existingDetails.setCity(cachingEn.getCity());
			}
			if(cachingEn.getPincode() != null) {
				existingDetails.setPincode(cachingEn.getPincode());
			}
			cachingRepo.save(existingDetails);
			response.put("status", HttpStatus.OK.value());
			response.put("message", "cachingEn details found with given cachId:"+cachId);
			response.put("findById", findById);
		}else {
			response.put("status", HttpStatus.NOT_FOUND.value());
			response.put("message", "cachingEn details not found with given cachId: "+cachId);
		}

		return response;
		
	}
}
