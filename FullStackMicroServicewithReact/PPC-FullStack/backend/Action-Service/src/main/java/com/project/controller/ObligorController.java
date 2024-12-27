package com.project.controller;

import com.project.Dto.ResponseQueryDto;
import com.project.Dto.RolesData;
import com.project.Dto.SpocData;
import com.project.entity.*;
import com.project.service.AuditTrailService;
import com.project.service.ObligorService;
import com.project.service.ResponseRemediationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/ActionObligor")
public class ObligorController {

    private static final Logger log = LoggerFactory.getLogger(ObligorController.class);
    @Autowired
    private ObligorService obligorService;

    @Autowired
    private ResponseRemediationService responseRemediationService;

    @Autowired
    private RolesData rolesData;

    @Autowired
    private AuditTrailService auditTrailService;

    @PostMapping("/save")
    public Map<String,Object> saveObligor(@RequestBody Obligor obligor) throws Exception {
        Map<String,Object> response = new HashMap<>();
        if(obligor != null){
            String generateChildReviewId = obligorService.generateChildReviewId(obligor.getReviewId());
            obligor.setChildReviewId(generateChildReviewId);
            obligorService.saveObligor(obligor);
            response.put("status", HttpStatus.OK.value());
            response.put("message","Obligor data saved successfully");
        }else {
            throw new Exception("Obligor is Empty");
        }
    return response;
    }

    @PutMapping("/update/Obligor")
    public Map<String,Object> updateObligorByActivityLevel(@RequestBody SpocData spocData){
        log.info("Entered Update Obligor By ActivityLevel with body : {}",spocData);
        String role = spocData.getRole().replaceAll("\\s+", "");
        log.info("roleWithoutSpaces in Obligor : {}",role);
        Map<String,Object> response = new HashMap<>();
        Obligor obligor = new Obligor();
        if(spocData.getAction()!=null){
            if( !spocData.getFieldwork().equals("FieldWorkCompleted")){
                Map<String,String> flowMatrix = rolesData.getMatrix().get(role).get(spocData.getAction());
                obligor.setReviewId(spocData.getReviewId());
                obligor.setChildReviewId(spocData.getChildReviewId());
                obligor.setAssignedTo(spocData.getAssignedTo());
                String roleSet = flowMatrix.get("activityLevel").replaceAll("\\s+", "");
                log.info("roleSet: {}",roleSet);
                obligor.setRole(roleSet);
                obligor.setRole(flowMatrix.get("activityLevel"));
                obligor.setTaskStatus(flowMatrix.get("caseStatus"));
                log.info("ObligorUpdate with body : {}",obligor);
                Obligor updateObligor = obligorService.updateObligor(obligor);
                AuditTrail setAuditTrail = new AuditTrail();
                setAuditTrail.setReviewId(obligor.getReviewId());
                setAuditTrail.setActionedBy(flowMatrix.get("activityLevel"));
                setAuditTrail.setCurrentAction(flowMatrix.get("auditText"));
                auditTrailService.saveAuditTrial(setAuditTrail);
                if(updateObligor != null){
                    response.put("status",HttpStatus.OK.value());
                    response.put("message","Obligor Successfully Updated...!");
                    response.put("result",updateObligor);
                    log.info("Obligor updated successfully : {}",updateObligor);
                }else{
                    response.put("status",HttpStatus.NOT_FOUND.value());
                    response.put("message","Failed to Update Obligor");
                    log.warn("Failed to update Obligor with spocData: {} ",spocData);
                }
            }else {
                obligor.setReviewStatus("Task Completed");
                obligor.setCreatedBy(spocData.getRole());
                obligor.setAssignedTo("");
                obligor.setReviewId(spocData.getReviewId());
                obligor.setChildReviewId(spocData.getChildReviewId());
                Obligor updateObligor = obligorService.updateObligor(obligor);
            }

        }else {
            response.put("status",HttpStatus.NOT_FOUND.value());
            response.put("message","Action is empty in spocData");
            log.warn("Action is Empty in SpocData : {}",spocData);
        }
        return response;
    }

    @PutMapping("/updateObligorByChildReviewId")
    public Map<String,Object> updateObligorBychildReviewId(@RequestBody Obligor obligor ){
        log.info("Enter updateObligorByChildReviewId with body : {}",obligor);
        Map<String,Object> response = new HashMap<>();

        try {
            Obligor result = obligorService.updateObligor(obligor);
            if(result.getChildReviewId() != null && !result.getChildReviewId().isEmpty()){
                response.put("status",HttpStatus.OK.value());
                response.put("message","Obligor Updated Successfully with childReviewId :"+obligor.getChildReviewId());
                response.put("result",result);
                log.info("Obligor Updated Successfully with childReviewId : {}",obligor.getChildReviewId());
            }else{
                response.put("status",HttpStatus.NOT_FOUND.value());
                response.put("message","Failed to update Obligor with childReviewId :"+obligor.getChildReviewId());
                log.warn("Failed to update Obligor with childReviewId : {}",obligor.getChildReviewId());
            }
        }catch (Exception e){
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "An error occurred while updating Obligor: " + e.getMessage());
            log.error("Error occurred while updating Obligor: ", e);
        }

      return response;
    }


    @DeleteMapping("/delete/{childReviewId}")
    public Map<String,Object> deleteObligor(@PathVariable String childReviewId){
        Map<String,Object> response = new HashMap<>();
        if(!childReviewId.isEmpty()){
            responseRemediationService.deleteResponse(childReviewId);
            obligorService.deleteObligor(childReviewId);
            response.put("status",HttpStatus.OK.value());
            response.put("message","Obligor Deleted Successfully...!");

        }else {
            throw new RuntimeException("ChildReviewId is Empty");
        }
        return response;
    }

    @PostMapping("/obligorDocument")
    public Map<String, Object> saveObligorDocument(
            @RequestParam("reviewId") String reviewId,
            @RequestParam("documentName") String documentName,
            @RequestParam("uploadedBy") String uploadedBy,
            @RequestParam("file") MultipartFile file) throws IOException {

        Map<String, Object> response = new HashMap<>();

        if (file.isEmpty()) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "File cannot be empty.");
            return response;
        }

        ObligorDocument details = new ObligorDocument();
        details.setReviewId(reviewId);
        details.setDocumentName(documentName);
        details.setUploadedBy(uploadedBy);
        details.setFile(file.getBytes());

        try {
            ObligorDocument result = obligorService.saveObligorDocument(details);

            if (result != null) {
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Obligor Document Saved Successfully!");
            } else {
                response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.put("message", "Failed to save Obligor document.");
            }
        } catch (Exception e) {
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("message", "Error occurred while saving Obligor document: " + e.getMessage());
        }

        return response;
    }

    @DeleteMapping("/deleteDoc/{obligorDocId}")
    public Map<String,Object> deleteObligorDoc(@PathVariable String obligorDocId){
        log.info("Entered deleteDoc with obligorDocId : {}",obligorDocId);
        Map<String,Object> response = new HashMap<>();
        obligorService.deleteObligorDoc(obligorDocId);
        response.put("status",HttpStatus.OK.value());
        response.put("message","ObligorDoc Deleted Successfully...!");
        return response;
    }

    @PostMapping("/saveResponseRemediation")
    public Map<String,Object> saveResponseRemediation(@RequestBody ResponseRemediation responseRemediation){
        log.info("Entered ResponseRemediation insert API with body : {}",responseRemediation);
        return responseRemediationService.saveResponseRemediation(responseRemediation);
    }

    @DeleteMapping("/deleteResponse/{childReviewId}")
    public Map<String,Object> deleteResponse(@PathVariable String childReviewId){
        log.info("Entered deleteResponse with childReviewId : {}",childReviewId);
        Map<String,Object> response = new HashMap<>();
        responseRemediationService.deleteResponse(childReviewId);
        response.put("status",HttpStatus.OK.value());
        response.put("message","Response Details Deleted Successfully...!");
        return response;
    }

    @PostMapping("/ResponseQuery/save")
    public Map<String,Object> saveResponseQueryDetails(@RequestBody ResponseQueryDetails responseQueryDetails){
        log.info("Entered ResponseQuery with body : {}",responseQueryDetails);
        Map<String,Object> response = new HashMap<>();
        if(responseQueryDetails.getQuery() != ""){
            String generateResponseQueryChildReviewId = responseRemediationService.generateChildReviewIdForResponseQuery(responseQueryDetails.getChildReviewId());
            log.info("generateResponseQueryChildReviewId : {}",generateResponseQueryChildReviewId);
            responseQueryDetails.setQuerySequence(generateResponseQueryChildReviewId);
            responseRemediationService.saveResponseQuery(responseQueryDetails);
            response.put("status",HttpStatus.OK.value());
            response.put("message","Response Query Saved Successfully...!");
            log.info("Response Query Saved Successfully...!");
        }else {
            response.put("status",HttpStatus.NO_CONTENT.value());
            response.put("message","Response Query Details are Empty");
            log.warn("Response Details are Empty with body : {}",responseQueryDetails);

        }
        return response;
    }

    @DeleteMapping("/deleteResponseQuery/{querySequence}")
    public Map<String,Object> deleteResponseQuery(@PathVariable String querySequence){
        log.info("Entered deleteResponseQuery with querySequence : {}",querySequence);
        Map<String,Object> response = new HashMap<>();
        responseRemediationService.deleteResponseQuery(querySequence);
        response.put("status",HttpStatus.OK.value());
        response.put("message","deleteResponseQuery Deleted Successfully...!");
        return response;
    }

    @PutMapping("/updateResponseQueryDetails")
    public Map<String,Object> updateResponse(@RequestBody ResponseQueryDto responseQueryDto){
        log.info("Enter Update ResponseQueryDetails with body : {}",responseQueryDto);
        Map<String,Object> response = new HashMap<>();
        ResponseQueryDto result = responseRemediationService.updateResponse(responseQueryDto);
        if(result.getQuerySequence().isEmpty()){
            response.put("status",HttpStatus.NO_CONTENT.value());
            response.put("message","failed To update ResponseQueryDetails");
            log.warn("failed to Update ResponseQueryDetails : {}",responseQueryDto);
        }else {
            response.put("status",HttpStatus.OK.value());
            response.put("message","ResponseQueryDetails updated Successfully");
            response.put("result",result);
            log.info("ResponseQueryDetails updated Successfully : {}",result);
        }
        return response;
    }

    @PutMapping("/updateResponseRemediation")
    public Map<String,Object> updateResponseRemediationByChildReviewId(@RequestBody ResponseRemediation responseRemediation){
        log.info("Entered Update ResponseRemediation with ChildReviewId : {}",responseRemediation);
        Map<String,Object> response = new HashMap<>();

        ResponseRemediation result = responseRemediationService.UpdateResponseRemediation(responseRemediation);
        if(result.getChildReviewId() != null){
            response.put("status",HttpStatus.OK.value());
            response.put("message","ResponseRemediation Updated Successfully...!");
            response.put("result",result);
            log.info("ResponseRemediation Updated Successfully : {}",result);
        }else {
            response.put("status",HttpStatus.NO_CONTENT.value());
            response.put("message"," failed to updated Response Remediation ");
            log.info("failed to updated Response Remediation");
        }
        return response;
    }

}

