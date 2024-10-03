package com.purna.service;

import com.purna.entity.AttachmentData;
import com.purna.entity.EmailEntity;
import com.purna.repository.EmailRepository;
import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.search.FlagTerm;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;
import java.util.Properties;

@Service
@Transactional
@Slf4j
public class EmailService {


    private final EmailRepository emailRepository;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

public EmailEntity testEmail(EmailEntity emailEntity){
    EmailEntity saveEmail = new EmailEntity();
    saveEmail.setId((long)0);
    saveEmail.setSubject(emailEntity.getSubject());
    saveEmail.setBody(emailEntity.getBody());
    return emailRepository.save(saveEmail);
}


    @Autowired
    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public void fetchEmails() {
        Properties properties = new Properties();
        properties.put("mail.imap.host", "imap.gmail.com");
        properties.put("mail.imap.port", "993");
        properties.put("mail.imap.ssl.enable", "true");
        properties.put("mail.imap.auth", "true");

        Session session = Session.getInstance(properties);

        try {
            Store store = session.getStore("imap");
            store.connect(host, username, password);
            log.info("host: {}, username: {}", host, username);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            if (messages != null && messages.length > 0) {
                for (Message message : messages) {
                    String subject = message.getSubject();
                    String body = getBodyFromMessage(message); // Get the body properly
                    AttachmentData attachmentData = getAttachmentsFromMessage(message); // Get the attachments as binary

                    log.info("Fetched email - Subject: {}, Body: {}", subject, body);

                    if (subject == null || body == null) {
                        log.warn("Skipping email with null subject or body.");
                        continue; // Skip this message if subject or body is null
                    }

                    if (!emailRepository.existsBySubject(subject)) {
                        EmailEntity emailEntity = new EmailEntity(subject, body, attachmentData.getAttachment(), attachmentData.getAttachmentType());
                        emailRepository.save(emailEntity);
                        // Example usage
                       // retrieveAndSaveAttachment(1L, "output.pdf");

                        log.info("Email saved successfully - Subject: {}", subject);
                    } else {
                        log.info("Email already exists in the database, skipping.");
                    }
                }
            } else {
                log.info("No new unread messages found.");
            }

            inbox.close(false);
            store.close();
        } catch (Exception e) {
            log.error("Error fetching emails", e);
        }
    }


    // Helper method to extract the body from a Message
    private String getBodyFromMessage(Message message) throws Exception {
        StringBuilder result = new StringBuilder();
        Object content = message.getContent();

        log.info("Message content type: {}", content.getClass());

        if (content instanceof String) {
            result.append((String) content); // Handle plain text
            log.info("Plain text body: {}", content);
        } else if (content instanceof MimeMultipart) {
            // Multipart email with text/plain and text/html
            result.append(getTextFromMultipart((MimeMultipart) content));
        } else {
            log.warn("Unexpected content type: {}", content.getClass());
        }

        return result.length() > 0 ? result.toString() : null; // Return null if no body
    }

    // Recursive method to extract text from Multipart
    private String getTextFromMultipart(MimeMultipart multipart) throws Exception {
        String plainTextPart = null;
        String htmlTextPart = null;

        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);

            log.info("Body part content type: {}", bodyPart.getContentType());

            if (bodyPart.isMimeType("text/plain")) {
                plainTextPart = bodyPart.getContent().toString(); // Capture plain text
                log.info("Plain text part: {}", plainTextPart);
            } else if (bodyPart.isMimeType("text/html")) {
                String htmlContent = bodyPart.getContent().toString();
                Document doc = Jsoup.parse(htmlContent);
                htmlTextPart = doc.text(); // Convert HTML to plain text
                log.info("HTML part converted to plain text: {}", htmlTextPart);
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                return getTextFromMultipart((MimeMultipart) bodyPart.getContent());
            }
        }

        // Prefer plain text part if available, otherwise use the HTML part
        return plainTextPart != null ? plainTextPart : htmlTextPart;

    }

    private AttachmentData getAttachmentsFromMessage(Message message) throws Exception {
        AttachmentData attachmentData = null;

        if (message.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) message.getContent();

            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);

                if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) || bodyPart.getFileName() != null) {
                    InputStream is = bodyPart.getInputStream();
                    byte[] attachmentBytes = is.readAllBytes(); // Read the attachment as bytes
                    String mimeType = bodyPart.getContentType().split(";")[0]; // Extract the MIME type

                    attachmentData = new AttachmentData(attachmentBytes, mimeType);

                    log.info("Attachment found: {} (MIME type: {})", bodyPart.getFileName(), mimeType);
                    break; // Save only the first attachment for now
                }
            }
        }

        return attachmentData;
    }

//    public void retrieveAndSaveAttachment(Long emailId, String outputFilePath) {
//        // Find email by ID or subject, depending on your query
//        Optional<EmailEntity> optionalEmailEntity = emailRepository.findById(emailId);
//
//        if (optionalEmailEntity.isPresent()) {
//            EmailEntity emailEntity = optionalEmailEntity.get();
//            byte[] pdfBytes = emailEntity.getAttachment(); // Get attachment
//
//            if (pdfBytes != null && pdfBytes.length > 0) {
//                try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
//                    fos.write(pdfBytes); // Write the binary data to a file
//                    log.info("PDF attachment saved to: {}", outputFilePath);
//                } catch (IOException e) {
//                    log.error("Error writing PDF to file", e);
//                }
//            } else {
//                log.info("No attachment found for email with ID: {}", emailId);
//            }
//        } else {
//            log.warn("No email found with ID: {}", emailId);
//        }
//    }



}
