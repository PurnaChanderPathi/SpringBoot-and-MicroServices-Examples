package com.project.Dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponseQueryDto {

    private String querySequence;
    private String query;
    private Timestamp createdOn;
    private String createdBy;
    private String response;
    private String responseBy;
    private Timestamp responseOn;
    private String reviewId;
    private String childReviewId;
}
