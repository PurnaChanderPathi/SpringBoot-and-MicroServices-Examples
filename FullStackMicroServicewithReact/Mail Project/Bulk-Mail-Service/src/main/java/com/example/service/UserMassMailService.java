package com.example.service;

import java.io.IOException;
import java.util.Map;

import com.example.dto.PasswordDto;
import com.example.dto.UploadExcelDto;
import com.example.exception.FolderDoesNotExistsException;

import jakarta.mail.MessagingException;

public interface UserMassMailService {

    Map<String, Object> massMails(UploadExcelDto excelDto) throws IOException, MessagingException;

    Map<String, Object> deleteFolder(String folderName) throws IOException, FolderDoesNotExistsException;

    Map<String, Object> getAllFolders();

    Map<String, Object> forgotPassword() throws MessagingException, IOException;

    Map<String, Object> resetPassword(PasswordDto passwordDto);


    Map<String, Object> getAllFiles(String folderName);

    Map<String, Object> deleteFile(String folderName, String fileName);

    Map<String, Object> getUnsentMailIds();
}
