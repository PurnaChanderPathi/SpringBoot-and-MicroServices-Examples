package com.broadcastMail.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailForm {

    private String from;

    private String subject;
    private String body;
    private String[] cc;
}
