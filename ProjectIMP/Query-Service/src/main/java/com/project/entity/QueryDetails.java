package com.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
