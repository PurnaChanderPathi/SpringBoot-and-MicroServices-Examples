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
    private String division;
    private String groupName;
    private String assignedTo;
    private String role;
    private String currentStatus;
    private Date createdDate;
    private String createdBy;
    private String planning;
    private String fieldwork;
    private String action;
}
