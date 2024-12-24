package com.project.Dto;

import com.project.entity.QueryDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiTableSearch {

    private String reviewId;
    private String division;
    private String groupName;
    private String assignedTo;
    private String role;
    private String currentStatus;
    private Timestamp createdOn;
    private String createdBy;
    private String childReviewId;
//    private String activityLevel;

    public MultiTableSearch(String reviewId, String division, String groupName, String assignedTo, String role, String currentStatus, String createdBy, String s, String s1) {
    }
}
