package com.example.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.StudentDto;
import com.example.model.Students;
import com.example.repo.StudentRepo;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepo studentRepo;
	
	private String saveStudent(StudentDto studentDto) {
		
		Students student = new Students();
		
		BeanUtils.copyProperties(studentDto, student);
		
	 studentRepo.save(student);
		return "Data inserted Successfully";
	}

}
