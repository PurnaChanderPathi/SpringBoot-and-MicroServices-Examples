package com.project.controller;

import com.project.entity.AuditTrail;
import com.project.service.AuditTrailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/ActionAT")
@Slf4j
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

    @PutMapping("/updateAudit")
    public Map<String,Object> updateAudit(@RequestBody AuditTrail auditTrail){
        log.info("Entered updateAudit with body: {}",auditTrail);
        Map<String,Object> response = new HashMap<>();

        if(auditTrail.getReviewId()!=null){
            try {
                AuditTrail updatedAudit = auditTrailService.updateAuditTrail(auditTrail);
                response.put("status",HttpStatus.OK.value());
                response.put("message","Audit Trail Updated Successfully...!");
                response.put("result",updatedAudit);
                log.info("Audit Trail Updated Successfully : {}",updatedAudit);
            }catch (RuntimeException e){
                response.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
                log.error("Internal Server Exception in Audit Trail Update");
            }

        }else {
            response.put("status",HttpStatus.NOT_FOUND.value());
            response.put("message","ReviewId is Empty in Audit Trail");
            log.warn("ReviewId is empty in Audit Trail : {}",auditTrail.getReviewId());
        }
        return response;
    }
}
