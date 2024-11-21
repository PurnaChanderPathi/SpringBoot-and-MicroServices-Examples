package com.project.controller;

import com.project.entity.AdminConfig;
import com.project.service.AdminConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminConfigService adminConfigService;

    @PostMapping("/save")
    public Map<String,Object> addAdminConfig(@RequestBody AdminConfig adminConfig){
        log.info("entered addAdminConfig");
        Map<String,Object> response = new HashMap<>();
        try {
            adminConfigService.addConfig(adminConfig);
            response.put("status", HttpStatus.OK.value());
            response.put("message","AdminConfig saved Successfully...!");
        }catch (Exception e){
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message","AdminConfig not saved :"+e.getMessage());
        }
    return response;
    }
}
