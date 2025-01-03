package com.project.Dto;

import lombok.Data;

@Data
public class QueryMultiSearch {
    private String reviewId;
    private String childReviewId;
    private String groupName;
    private String division;
    private String currentStatus;
    private String assignedTo;
    private String createdBy;
    private String role;

}
