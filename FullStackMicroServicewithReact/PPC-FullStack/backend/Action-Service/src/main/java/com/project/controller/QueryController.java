package com.project.controller;

import com.project.Dto.RequestData;
import com.project.Dto.RolesData;
import com.project.entity.AuditTrail;
import com.project.entity.QueryDetails;
import com.project.service.AuditTrailService;
import com.project.service.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/action")
@Slf4j
public class QueryController {

    @Autowired
    private QueryService queryService;

    @Autowired
    private AuditTrailService auditTrailService;

    @Autowired
    private RolesData rolesData;

//    @PostMapping(value = "/save",consumes = MediaType.APPLICATION_JSON_VALUE)
//    public String createQuery(@RequestBody QueryDetails queryDetails){
//        return queryService.saveQueryDetails(queryDetails);
//    }

    @PostMapping("/save")
    public Map<String,Object> createQuery(@RequestBody QueryDetails queryDetails){
        log.info("QueryDetails : {}",queryDetails);
        Map<String,Object> response = new HashMap<>();
         queryService.saveQueryDetails(queryDetails);
        AuditTrail setAuditTrail = new AuditTrail();
        setAuditTrail.setReviewId(queryDetails.getReviewId());
        setAuditTrail.setActionedBy("SrCreditReviewer");
        setAuditTrail.setCurrentAction("Case Created by SrCreditReviewer");
        auditTrailService.saveAuditTrial(setAuditTrail);
        response.put("status", HttpStatus.OK.value());
        response.put("message","ReviewId Successfully Created");
        return response;
 }

//    @PutMapping("/update")
//    public QueryDetails updateQuery(@RequestBody QueryDetails queryDetails){
//
//        return queryService.updateQuery(queryDetails);
//    }

    @PutMapping("/update")
    public Map<String,Object> updateQuery(@RequestBody QueryDetails queryDetails){
        log.info("QueryDetails updated : {}",queryDetails);
        Map<String,Object> response = new HashMap<>();
        QueryDetails details = queryService.updateQuery(queryDetails);
        AuditTrail auditTrail = new AuditTrail();
        auditTrail.setReviewId(queryDetails.getReviewId());
        auditTrail.setActionedBy(queryDetails.getRole());
         auditTrailService.saveAuditTrial(auditTrail);
        if(details != null){
            response.put("status",HttpStatus.OK.value());
            response.put("message","Successfully Updated...!");
            response.put("result",details);
        }else{
            response.put("status",HttpStatus.NOT_FOUND.value());
            response.put("message","Not Updated Successfully...!");
        }
        return response;
    }

    @PutMapping("/submitTask")
    public Map<String,Object> submitTask(@RequestBody RequestData requestData){
        log.info("RequestData Update:- {}",requestData);
        String role = requestData.getRole().replaceAll("\\s+", "");
        log.info("roleWithoutSpaces : {}",role);
        Map<String,Object> response = new HashMap<>();
        if(requestData!=null){
            Map<String,String> flowMatrix = rolesData.getMatrix().get(role).get(requestData.getAction());

            QueryDetails queryDetails=new QueryDetails();
            queryDetails.setReviewId(requestData.getReviewId());
            String roleSet = flowMatrix.get("activityLevel").replaceAll("\\s+", "");
            log.info("roleSet: {}",roleSet);
            queryDetails.setRole(roleSet);
//            queryDetails.setRole(flowMatrix.get("activityLevel"));
            queryDetails.setCurrentStatus(flowMatrix.get("caseStatus"));
            queryDetails.setPlanning(requestData.getPlanning());
            queryDetails.setAction(requestData.getAction());
            queryDetails.setAssignedTo(requestData.getAssignedTo());
            queryDetails.setFieldwork(requestData.getFieldwork());
            QueryDetails updatedData = queryService.updateQuery(queryDetails);
            log.info("updatedData");
//            AuditTrail setAuditTrail = new AuditTrail();
//            setAuditTrail.setReviewId(queryDetails.getReviewId());
//            setAuditTrail.setActionedBy(flowMatrix.get("activityLevel"));
//            setAuditTrail.setCurrentAction(flowMatrix.get("auditText"));
//            AuditTrail updatedAudit = auditTrailService.updateAuditTrail(setAuditTrail);
            if(updatedData != null){
                response.put("status",HttpStatus.OK.value());
                response.put("message","Successfully Updated...!");
                response.put("result",updatedData);
//                response.put("Audit",updatedAudit);
            }else{
                response.put("status",HttpStatus.NOT_FOUND.value());
                response.put("message","Not Updated Successfully...!");
            }
        }else{
            throw new RuntimeException("RequestData is null");
        }

        return response;

    }

    @DeleteMapping("/{reviewId}")
    public String deleteQuery(@PathVariable String reviewId){
        return queryService.deleteByReviewId(reviewId);
    }

}
