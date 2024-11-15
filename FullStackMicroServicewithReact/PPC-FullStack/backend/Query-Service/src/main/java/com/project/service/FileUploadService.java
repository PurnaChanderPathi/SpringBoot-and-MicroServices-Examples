package com.project.service;

import com.project.Dto.FileData;
import com.project.entity.UploadedFile;
import com.project.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileUploadService {

    @Autowired
    private FileRepository fileRepository;


    public FileData getFileData(Long fileId){
        return fileRepository.getFileByFileId(fileId);
    }

    public List<UploadedFile> getFileByReviewId(String reviewId){
        return fileRepository.getFileByReviewId(reviewId);
    }
}
