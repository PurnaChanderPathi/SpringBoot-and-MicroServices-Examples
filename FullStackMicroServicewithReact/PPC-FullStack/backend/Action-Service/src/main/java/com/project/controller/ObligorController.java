package com.project.controller;

import com.project.entity.Obligor;
import com.project.entity.ObligorDocument;
import com.project.service.ObligorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
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

    @DeleteMapping("/delete/{obligorId}")
    public Map<String,Object> deleteObligor(@PathVariable String obligorId){
        Map<String,Object> response = new HashMap<>();
        obligorService.deleteObligor(obligorId);
        response.put("status",HttpStatus.OK.value());
        response.put("message","Obligor Deleted Successfully...!");
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
}

