package com.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadedFile {

    private String fileId;

    private String reviewId;

    private String fileName;

    private String fileType;

    private byte[] data;

    private Long size;

    private String comment;
}
