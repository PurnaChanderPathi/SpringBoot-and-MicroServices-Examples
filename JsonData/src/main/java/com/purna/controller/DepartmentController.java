package com.purna.controller;

import com.purna.entity.Department;
import com.purna.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/Department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/saveDepartment")
    private ResponseEntity<Map<String, Object>> saveDepartment(@RequestBody Department department){
        Map<String,Object> response = new HashMap<>();
        Department saveDepartment = departmentService.saveDepartment(department);
        response.put("status", HttpStatus.OK.value());
        response.put("message","Department details saved successfully..!");
        return ResponseEntity.ok().body(response);
    }


}
