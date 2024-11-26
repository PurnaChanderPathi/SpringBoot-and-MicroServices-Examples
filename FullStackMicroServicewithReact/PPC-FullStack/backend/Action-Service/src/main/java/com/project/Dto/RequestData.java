package com.project.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class RequestData {

    @JsonProperty("")
    private String reviewId;
    private String currentStatus;
    private String action;
    private String role;
    private String assignedTo;
    private String planning;
    private String fieldwork;
}
