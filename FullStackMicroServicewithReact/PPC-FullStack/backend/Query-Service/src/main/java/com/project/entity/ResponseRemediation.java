package com.project.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponseRemediation {

    private int responseRemediationId;
    private String reviewId;
    private String obligorName;
    private String obligorCifId;
    private String obligorPremId;
    private String groupName;
    private String createdBy;
    private Timestamp createdOn;
    private String reviewStatus;
    private String childReviewId;
    private String division;
    private String isActive;
    private String observation;
    private String taskStatus;
    private String activityLevel;
}
