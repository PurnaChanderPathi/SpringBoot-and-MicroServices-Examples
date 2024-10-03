package com.purna.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public class EmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String body;

    @Lob
    private byte[] attachment; // Field for saving attachment as binary

    @Column(nullable = true)
    private String attachmentType; // Type of the attachment (image/jpeg, application/pdf, etc.)

    // Constructors, getters, and setters
    public EmailEntity() {}

    public EmailEntity(String subject, String body, byte[] attachment, String attachmentType) {
        this.subject = subject;
        this.body = body;
        this.attachment = attachment;
        this.attachmentType = attachmentType;
    }

    // Getters and setters
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public byte[] getAttachment() {
        return attachment;
    }

    public void setAttachment(byte[] attachment) {
        this.attachment = attachment;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }

    public void setId(long l) {
    }
}
