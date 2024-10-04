package com.purna.controller;

import com.purna.entity.Student;
import com.purna.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/saveStudent")
    private ResponseEntity<Map<String,Object>> saveStudent(@RequestBody Student student){
        Map<String,Object> response = new HashMap<>();
        Student result = studentService.saveStudent(student);
        response.put("status", HttpStatus.OK.value());
        response.put("message","Student Details saved successfully...!");
        return ResponseEntity.ok().body(response);
    }
}
