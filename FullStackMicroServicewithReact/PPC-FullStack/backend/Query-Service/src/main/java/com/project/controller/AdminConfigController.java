package com.project.controller;

import com.project.service.AdminConfigService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/adminConfig")
@Slf4j
public class AdminConfigController {

    @Autowired
    private AdminConfigService adminConfigService;

    @GetMapping("/getGroupNames")
    public Map<String,Object> getGroupNames(){
        log.info("Entered getGroupNames");
        Map<String,Object> response = new HashMap<>();
        List<String> getGroupNames = adminConfigService.getGroupNames();
        log.info("getGroupNames ; {}",getGroupNames);
        if(!getGroupNames.isEmpty()){
            response.put("status", HttpStatus.OK.value());
            response.put("message","GroupNames Fetched Successfully...!");
            log.info("GroupNames Successfully Fetched");
            response.put("result",getGroupNames);
        }else {
            response.put("status",HttpStatus.NOT_FOUND.value());
            response.put("message","Failed to Fetch GroupNames");
            log.error("Failed to Fetch GroupNames");
        }
        return response;
    }

    @GetMapping("/getDivisions/{groupName}")
    public Map<String,Object> getDivisions(@PathVariable String groupName){
        log.info("Entered getDivisions");
        Map<String,Object> response = new HashMap<>();
        List<String> result = adminConfigService.getDivisions(groupName);
        if(!result.isEmpty()){
            response.put("status",HttpStatus.OK.value());
            response.put("message","Divisions Fetched Successfully...!");
            log.info("Divisions Fetched Successfully...!");
            response.put("result",result);
        }else {
            response.put("status",HttpStatus.NOT_FOUND.value());
            response.put("message","Failed To Fetch Divisions");
            log.error("Failed To Fetch Divisions");
        }
        return response;
    }

    @GetMapping("/getSpocs/{division}")
    public Map<String,Object> getSpocs(@PathVariable String division){
        log.info("Entered getSpocs");
        Map<String,Object> response = new HashMap<>();
        List<String> result = adminConfigService.getSpocs(division);
        if(!result.isEmpty()){
            List<String> spocList = Arrays.asList(result.get(0).split(","));
            Collections.reverse(spocList);
            response.put("status",HttpStatus.OK.value());
            response.put("message","Spocs Fetched Successfully...!");
            log.info("Spocs Fetched Successfully...!");
            response.put("result",spocList);
        }else {
            response.put("status",HttpStatus.NOT_FOUND.value());
            response.put("message","Failed To Fetch Spocs");
            log.error("Failed To Fetch Spocs");
        }
        return response;
    }
}
