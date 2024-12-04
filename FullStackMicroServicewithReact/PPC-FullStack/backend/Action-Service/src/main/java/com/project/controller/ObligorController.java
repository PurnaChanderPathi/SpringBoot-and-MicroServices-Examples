package com.project.controller;

import com.project.entity.Obligor;
import com.project.service.ObligorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/ActionObligor")
public class ObligorController {

    @Autowired
    private ObligorService obligorService;

    @PostMapping("/save")
    public Map<String,Object> saveObligor(@RequestBody Obligor obligor) throws Exception {
        Map<String,Object> response = new HashMap<>();
        if(obligor != null){
            obligorService.saveObligor(obligor);
            response.put("status", HttpStatus.OK.value());
            response.put("message","Obligor data saved successfully");
        }else {
            throw new Exception("Obligor is Empty");
        }
    return response;
    }
}
