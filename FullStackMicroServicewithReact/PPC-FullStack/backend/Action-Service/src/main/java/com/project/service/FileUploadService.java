package com.project.service;

import com.project.Dto.FileData;
import com.project.entity.UploadedFile;
import com.project.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileUploadService {

    @Autowired
    private FileRepository fileRepository;

    public String saveFile(MultipartFile file, String reviewId, String comment) throws IOException {
        UploadedFile uploadedFile = new UploadedFile();
        uploadedFile.setFileName(file.getOriginalFilename());
        uploadedFile.setFileType(file.getContentType());
        uploadedFile.setData(file.getBytes());
        uploadedFile.setSize(file.getSize());
        uploadedFile.setReviewId(reviewId);
        uploadedFile.setComment(comment);

        String result = fileRepository.saveFile(uploadedFile);

        if(!result.isEmpty()){
            return "File uploaded successfully..!"+file.getOriginalFilename();
        }else{
            return "File upload failed";
        }
    }

//    public FileData getFileData(Long fileId){
//        return fileRepository.getFileByFileId(fileId);
//    }

    public String deleteByFileId(Long fileId){
        return fileRepository.deleteByFileId(fileId);
    }
}
