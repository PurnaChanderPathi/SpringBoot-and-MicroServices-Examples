package com.project.controller;

import com.project.entity.AuditTrail;
import com.project.service.AuditTrailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/ActionAT")
public class AuditTrialController {

    @Autowired
    private AuditTrailService auditTrailService;

    @PostMapping("/save")
    public Map<String,Object> saveAudit(@RequestBody AuditTrail auditTrail){
        Map<String,Object> response = new HashMap<>();
        String saveAudit = auditTrailService.saveAuditTrial(auditTrail);
        response.put("status", HttpStatus.OK.value());
        response.put("message","Audit Trail Inserted Successfully...!");
        return response;
    }
}
