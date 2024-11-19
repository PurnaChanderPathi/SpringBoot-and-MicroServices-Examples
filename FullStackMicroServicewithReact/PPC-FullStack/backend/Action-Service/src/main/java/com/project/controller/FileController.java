package com.project.controller;

import com.project.Dto.FileData;
import com.project.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public Map<String,Object> fileUpload(@RequestParam("file") MultipartFile file,
                                         @RequestParam String reviewId,
                                         @RequestParam String comment) throws IOException {
        Map<String,Object> response = new HashMap<>();
        String fileUpload = fileUploadService.saveFile(file,reviewId,comment);
        response.put("status", HttpStatus.OK.value());
        response.put("message","File Uploaded Successfully..!");
        return response;
    }



//    @GetMapping("/View/{fileId}")
//    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long fileId) {
//        FileData fileData = fileUploadService.getFileData(fileId);
//
//        if (fileData == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileData.getFileName() + "\"")
//                .header(HttpHeaders.CONTENT_TYPE, fileData.getContentType())
//                .body(fileData.getFileData());
//    }

    @DeleteMapping("/delete/{fileId}")
    public Map<String,Object> deleteByFileId(@PathVariable Long fileId){
        Map<String,Object> response = new HashMap<>();
        String result = fileUploadService.deleteByFileId(fileId);
        if(result == null){
            response.put("status", HttpStatus.NOT_FOUND.value());
            response.put("message", "File not found or could not be deleted");
        }else{
            response.put("status",HttpStatus.OK.value());
            response.put("message","File Deleted Successfully");
        }
        return response;
    }
}
