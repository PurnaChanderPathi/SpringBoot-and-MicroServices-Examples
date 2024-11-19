package com.project.Dto;

import lombok.Data;

@Data
public class RequestData {

    private String reviewId;
    private String currentStatus;
    private String action;
    private String role;
    private String assignedTo;
}
