package com.broadcastMail.service;

import com.broadcastMail.dto.SigninDto;
import com.broadcastMail.dto.UploadExcelDto;
import com.broadcastMail.entites.MailCredentials;
import com.broadcastMail.entites.User;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public interface UserMailService {
    Map<String, Object> createUser(SigninDto signinDto);

  //  Map<String, Object> uploadExcel(UploadExcelDto excelDto) throws IOException;

    Map<String, Object> saveMailCredentials(MailCredentials credentials);

    Map<String, Object> getAllMailCredentials();

}
