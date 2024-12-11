package com.project.controller;

import com.project.Dto.FileData;
import com.project.entity.Obligor;
import com.project.entity.ObligorDocument;
import com.project.entity.ResponseRemediation;
import com.project.service.ObligorService;
import com.project.service.ResponseRemediationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/QueryObligor")
@Slf4j
public class ObligorController {

    @Autowired
    private ObligorService obligorService;

    @Autowired
    private ResponseRemediationService responseRemediationService;

    @GetMapping("/getObligorDetailsByReviewId")
    public Map<String,Object> getObligorDetailsByReviewId(@RequestParam String reviewId){
        log.info("Enterted getObligorDetailsByReviewId with body : {}",reviewId);
        Map<String,Object> response = new HashMap<>();
        List<Obligor> result = obligorService.getObligorDetails(reviewId);
        if(result.isEmpty()){
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message","Obligor Details not found with reviewId :"+reviewId);
            log.warn("Obligor Details not found with reviewId : {}",reviewId);
        }else {
            response.put("status",HttpStatus.OK.value());
            response.put("message","Obligor Details Fetched Successfully...!");
            response.put("result",result);
            log.info("Obligor Details Fetched Successfully with reviewId : {}",reviewId);
        }
        return response;
    }

    @GetMapping("/getObligorDocumentByReviewId")
    public Map<String,Object> getObligorDocumentByReviewId(@RequestParam String reviewId){
        log.info("Enterted getObligorDocumentByReviewId with body : {}",reviewId);
        Map<String,Object> response = new HashMap<>();
        List<ObligorDocument> result = obligorService.getObligorDocumentDetails(reviewId);
        if(result.isEmpty()){
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message","Obligor Details not found with reviewId :"+reviewId);
            log.warn("Obligor Details not found with reviewId : {}",reviewId);
        }else {
            response.put("status",HttpStatus.OK.value());
            response.put("message","Obligor Document Fetched Successfully...!");
            response.put("result",result);
            log.info("Obligor Document Fetched Successfully with reviewId : {}",reviewId);
        }
        return response;
    }

    @GetMapping("/View/{obligorDocId}")
    public ResponseEntity<byte[]> ViewPdf(@PathVariable String obligorDocId) {
        log.info("Entered view Obligor Document with ObligorDocId : {}", obligorDocId);
        ObligorDocument obligorDocument = obligorService.getObligorDocument(obligorDocId);

        if (obligorDocument == null) {
            return ResponseEntity.notFound().build();
        }

        log.info("Found document: {}", obligorDocument.getDocumentName());

        String contentType = "application/octet-stream";
        if (obligorDocument.getDocumentName().endsWith(".pdf")) {
            contentType = "application/pdf";
        } else if (obligorDocument.getDocumentName().endsWith(".jpg") || obligorDocument.getDocumentName().endsWith(".jpeg")) {
            contentType = "image/jpeg";
        } else if (obligorDocument.getDocumentName().endsWith(".png")) {
            contentType = "image/png";
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + obligorDocument.getDocumentName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(obligorDocument.getFile());
    }

    @GetMapping("/findByChildReviewId/{childReviewId}")
    public Map<String,Object> findByChildReviewId(@PathVariable String childReviewId){
        log.info("Entered findByChildReviewId with childReviewId of Obligor : {}",childReviewId);
        Map<String,Object> response = new HashMap<>();

        Obligor result = obligorService.getObligorByChildReviewId(childReviewId);

        if(result!= null){
            response.put("status",HttpStatus.OK.value());
            response.put("message","Obligor Details Fetched Successfully with childReviewId");
            response.put("result",result);
            log.info("Obligor Details Fetched Successfully with childReviewId : {}",childReviewId);
        }else {
            response.put("status",HttpStatus.NOT_FOUND.value());
            response.put("message","Failed to Fetch obligor Details with childReviewId"+childReviewId);
            log.warn("Failed to Fetch obligor Details with childReviewId : {}",childReviewId);
        }
        return response;
    }

    @GetMapping("findByChildReviewIdOfResponse/{childReviewId}")
    public Map<String,Object> findByChildReviewIdOfResponse(@PathVariable String childReviewId){
        log.info("Entered findByChildReviewId with childReviewId of ResponseRemediation : {}",childReviewId);
        Map<String,Object> response = new HashMap<>();

        ResponseRemediation result = responseRemediationService.findByChildReviewId(childReviewId);

        if(result != null){
            response.put("status",HttpStatus.OK.value());
            response.put("message","Response Remediation details Fetched Successfully with childReviewId"+childReviewId);
            response.put("result",result);
            log.info("Response Details fetched with childReviewId : {}",result);
        }else {
            response.put("status",HttpStatus.NOT_FOUND.value());
            response.put("message"," Failed to Fetch Response Remediation details with childReviewId"+childReviewId);
            log.info(" Failed to Fetch Response Remediation details with childReviewId : {}",childReviewId);
        }
        return response;
    }

}
