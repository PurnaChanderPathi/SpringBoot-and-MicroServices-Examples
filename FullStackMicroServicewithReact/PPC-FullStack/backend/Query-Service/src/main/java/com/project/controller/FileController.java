package com.project.controller;

import com.project.Dto.FileData;
import com.project.Dto.RolesData;
import com.project.entity.UploadedFile;
import com.project.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/query/file")
@Slf4j
public class FileController {

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private RolesData rolesData;

    @GetMapping("/View/{fileId}")
    public ResponseEntity<byte[]> ViewPdf(@PathVariable Long fileId) {
        FileData fileData = fileUploadService.getFileData(fileId);
//        Map<String, String> map=rolesData.getMatrix().get("matrix");
//        log.info("map : {}",map);

        if (fileData == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileData.getFileName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, fileData.getContentType())
                .body(fileData.getFileData());
    }

    @GetMapping("/findByReviewId/{reviewId}")
    public Map<String,Object> getFilesByReviewId(@PathVariable String reviewId){
        Map<String,Object> response = new HashMap<>();
        List<UploadedFile> fetchedFiles = fileUploadService.getFileByReviewId(reviewId);
        if(fetchedFiles!=null){
            response.put("status",HttpStatus.OK.value());
            response.put("message","Files Fetched Successfully...!");
            response.put("result",fetchedFiles);
        }else {
            response.put("status",HttpStatus.NOT_FOUND.value());
            response.put("message","Files not found with reviewId");
        }
        return response;
    }
}
