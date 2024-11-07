package com.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryDetails {
    private String reviewId;
    private String childReviewId;
    private String issueId;
    private String trackIssueId;
    private String division;
    private String groupName;
    private String taskStatus;
    private String assignedToUser;
    private String role;
    private String currentStatus;
    private Date createdDate;
    private String createdBy;
}
