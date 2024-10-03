package com.purna.controller;

import com.purna.entity.EmailEntity;
import com.purna.repository.EmailRepository;
import com.purna.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    private EmailRepository emailRepository;

    @GetMapping("/fetchMails")
    public String fetchEmail(){
        emailService.fetchEmails();
        return "Emails fetched successfully!";
    }

    @PostMapping("/testMail")
    public EmailEntity testEmail(@RequestBody EmailEntity emailEntity){
         return emailService.testEmail(emailEntity);
    }


    @GetMapping("/attachment/{id}")
    public ResponseEntity<byte[]> getAttachment(@PathVariable Long id) {
        Optional<EmailEntity> emailEntityOpt = emailRepository.findById(id);

        if (emailEntityOpt.isPresent()) {
            EmailEntity emailEntity = emailEntityOpt.get();

            if (emailEntity.getAttachment() != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(emailEntity.getAttachmentType()));
                headers.setContentDisposition(ContentDisposition.inline()
                        .filename("attachment" + id + getExtensionFromMimeType(emailEntity.getAttachmentType()))
                        .build());

                return new ResponseEntity<>(emailEntity.getAttachment(), headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Helper method to determine file extension based on MIME type
    private String getExtensionFromMimeType(String mimeType) {
        if (mimeType.equals("image/jpeg")) {
            return ".jpg";
        } else if (mimeType.equals("image/png")) {
            return ".png";
        } else if (mimeType.equals("application/pdf")) {
            return ".pdf";
        } else {
            return "";
        }
    }

}
