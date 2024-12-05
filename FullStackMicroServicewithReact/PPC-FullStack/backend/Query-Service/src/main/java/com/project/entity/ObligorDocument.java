package com.project.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ObligorDocument {

    private String obligorDocId;
    private String reviewId;
    private String documentName;
    private Timestamp uploadedOn;
    private String uploadedBy;
    private byte[] file;
}
