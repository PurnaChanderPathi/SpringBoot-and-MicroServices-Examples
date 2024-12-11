package com.project.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponseRemediation {

    @JsonProperty("responseRemediationId")
    private int responseRemediationId;
    @JsonProperty("reviewId")
    private String reviewId;
    @JsonProperty("obligorName")
    private String obligorName;
    @JsonProperty("obligorCifId")
    private String obligorCifId;
    @JsonProperty("obligorPremId")
    private String obligorPremId;
    @JsonProperty("groupName")
    private String groupName;
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("createdOn")
    private Timestamp createdOn;
    @JsonProperty("reviewStatus")
    private String reviewStatus;
    @JsonProperty("childReviewId")
    private String childReviewId;
    @JsonProperty("division")
    private String division;
    @JsonProperty("isActive")
    private String isActive;
    @JsonProperty("observation")
    private String observation;
    @JsonProperty("taskStatus")
    private String taskStatus;
    @JsonProperty("activityLevel")
    private String activityLevel;
}
