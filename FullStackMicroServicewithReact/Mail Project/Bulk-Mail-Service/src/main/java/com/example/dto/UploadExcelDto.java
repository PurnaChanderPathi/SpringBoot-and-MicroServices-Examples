package com.example.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadExcelDto {

    private String username;
    private String cc;
    private String password;
    private MultipartFile file;
    private MultipartFile zipFile;
    private String subject;
    private String body;
}
