package com.example.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.dto.StudentDto;
import com.example.model.AadharDetails;
import com.example.model.Students;
import com.example.repo.StudentRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AadharService {

	@Autowired
	private StudentRepo studentRepo;

	WebClient webClient = WebClient.create();

	private static final String restUrl = "http://localhost:9192/getEmployees";

//	public String getAllEmployes() {
//		 Flux<AadharDetails> getAllEmployes = webClient.get().uri(restUrl).retrieve().bodyToFlux(AadharDetails.class);
//		 System.out.println(getAllEmployes);
//		 return "AllEmployes Printed";
//		
//	}

	List<String> names = new ArrayList<>();

	/*public void fetchDataAndPrint() {
		Flux<AadharDetails> aadharDataFlux = webClient.get().uri(restUrl).retrieve().bodyToFlux(AadharDetails.class);

		aadharDataFlux.subscribe(aadharData -> {
			// printDatatoconsole
			System.out.println("aadharId: " + aadharData.getAadharId());
			System.out.println("name: " + aadharData.getName());
			System.out.println("state: " + aadharData.getState());

			StudentDto studentDto = new StudentDto();
			if (aadharData.getName() != null) {
				studentDto.setName(aadharData.getName());
			}

			// BeanUtils.copyProperties(students, studentDto);
			Students students = new Students((long) 0, studentDto.getName());
			studentRepo.save(students);

			// Save username to ArrayList
			name.add(aadharData.getName());

			name.forEach(y -> System.out.println("names : " + y));
		});

		// Print usernames ArrayList

	}*/

	public void getEmployees() {

		List<AadharDetails> aadharResponse = webClient.get().uri(restUrl).retrieve().bodyToFlux(AadharDetails.class)
				.collect(Collectors.toList()).share().block();

		aadharResponse.forEach(y -> names.add(y.getName()));
		
		System.out.println(names);
		System.out.println("=========");
		
		List<Students> res  =  names.stream().map(name ->  new Students(0l, name)).collect(Collectors.toList());
		
		studentRepo.saveAll(res);
	}

//	public void fetchData() {
//		Mono<String> resposeMono = webClient.get().uri(restUrl).retrieve().bodyToMono(String.class);
//		
//		resposeMono.subscribe(response -> {
//			try {
//				List<String> usernames = extractNamesFromJson(response);
//				System.out.print("Usernames: " + usernames);
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		});
//	}
//
//	
//	private List<String> extractNamesFromJson(String jsonResponse) throws IOException {
//		ObjectMapper objectMapper = new ObjectMapper();
//		List<String> names = new ArrayList<>();
//		
//		JsonNode jsonNode = objectMapper.readTree(jsonResponse);
//		
//		for(JsonNode node : jsonNode) {
//			String name = node.get("name").asText();
//			names.add(name);
//		}
//		
//		return names;
//	}

}
