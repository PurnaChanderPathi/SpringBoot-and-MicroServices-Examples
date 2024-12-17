package com.project.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponseQueryDetails {

    private int resQueryId;
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
