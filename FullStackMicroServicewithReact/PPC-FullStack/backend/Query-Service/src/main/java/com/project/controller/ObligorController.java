package com.project.controller;

import com.project.entity.Obligor;
import com.project.entity.ObligorDocument;
import com.project.service.ObligorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/QueryObligor")
@Slf4j
public class ObligorController {

    @Autowired
    private ObligorService obligorService;

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
}
