package com.purna.controller;

import com.purna.dto.StudentDepartmentDto;
import com.purna.entity.CollageDetails;
import com.purna.service.CollageDetailsService;
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
@RequestMapping("/CollageDetails")
public class CollageDetailsController {

    @Autowired
    private CollageDetailsService collageDetailsService;

    @PostMapping("/saveCollageDetails")
    private ResponseEntity<Map<String,Object>> saveCollageDetails(@RequestBody CollageDetails collageDetails){
        Map<String,Object> response = new HashMap<>();
        CollageDetails saveCollageDetails = collageDetailsService.saveCollageDetails(collageDetails);
        response.put("status", HttpStatus.OK.value());
        response.put("message","CollageDetails saved successfully..!");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/save")
    public ResponseEntity<CollageDetails> saveCollageDetails(@RequestBody StudentDepartmentDto dto){
        CollageDetails collageDetails = collageDetailsService.saveCollageDetails(dto);
        return ResponseEntity.ok().body(collageDetails);
    }
}
