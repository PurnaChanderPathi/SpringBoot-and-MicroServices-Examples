package com.broadcastMail.service;


import com.broadcastMail.dto.PasswordDto;
import com.broadcastMail.entites.*;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface MailService {
    String login(String email,String password);

    String signUp(SignUp signUp);


    MailCredentials getByMail(String from);


    void uploadExcel(EmailForm emailForm, MultipartFile to, MultipartFile zip, MailCredentials credentials) throws IOException;

    void saveMailCredentials(String email, String password);

    List<MailCredentials> getAll();

    void deleteFolder(String folderName) throws IOException;

    List<FolderEntity> getFileData();

    List<String> getAllFiles(String folderName);

    void deleteFile(String folderName,String fileName);

    List<FolderEntity> getAllFolders();

    void forgotPassword() throws MessagingException, IOException;

    void registerSave(MailCredentials mailCredentials);

    void deleteMailCredentials(long id);

    void changePassword(PasswordDto dto) throws MessagingException, IOException;

    void deleteSelectedFiles(String folderName, List<String> selectedFiles);

    boolean authenticateUser(String email);
}
