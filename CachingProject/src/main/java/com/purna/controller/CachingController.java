package com.purna.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.purna.model.CachingEn;
import com.purna.service.CachingService;

@RestController
@RequestMapping("/api/v1/caching")
public class CachingController {
	
	@Autowired
	private CachingService cachingService;
	
	@PostMapping("/save")
	public ResponseEntity<CachingEn> saveCh(@RequestBody CachingEn cachingEn){
		return ResponseEntity.ok().body(cachingService.saveCh(cachingEn));
	}
	
	@GetMapping("/findBycachId/{cachId}")
	public ResponseEntity<Optional<CachingEn>> findById(@PathVariable Long cachId){
		return ResponseEntity.ok().body(cachingService.findByCachId(cachId));
	}
	
	@GetMapping("/findByName")
	public ResponseEntity<CachingEn> findByName(@RequestParam String name){
		return ResponseEntity.ok().body(cachingService.findByName(name));
	}
	
	@GetMapping("/findByEmail")
	public ResponseEntity<CachingEn> findByEmail(@RequestParam String email){
		return ResponseEntity.ok().body(cachingService.findByEmail(email));
	}
	
	@DeleteMapping("delete/{cachId}")
	public ResponseEntity<String> deleteById(@PathVariable Long cachId){
		String result = "";
		CachingEn findById = cachingService.findByCachId(cachId).get();
		if(findById!=null) {
			cachingService.deleteCache(cachId);
			result = "CachingEn is deleted with CachId: "+cachId;
		}else {
			result = "CachingEn is notFound with CachId: "+cachId;
		}
		return ResponseEntity.ok().body(result);
	}
	
	@PutMapping("/update/{cachId}")
	public ResponseEntity<Map<String, Object>> updateCaching(
			@PathVariable Long cachId,
			@RequestBody CachingEn cachingEn
			){
		Map<String, Object> result = cachingService.updateEn(cachId,cachingEn);
				return ResponseEntity.ok().body(result);
		
	}
}
