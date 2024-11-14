package com.project.controller;

import com.project.entity.AuditTrail;
import com.project.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/QueryAT")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @GetMapping("/GetAudit/{reviewId}")
    public Map<String,Object> getAuditByReviewId(@PathVariable String reviewId){
        Map<String,Object> response = new HashMap<>();

        List<AuditTrail> getAuditDetails = auditService.getAllAuditByReviewId(reviewId);
        response.put("status", HttpStatus.OK.value());
        response.put("message","Audit Details Fetched successfully");
        response.put("result",getAuditDetails);
        return response;
    }


}
