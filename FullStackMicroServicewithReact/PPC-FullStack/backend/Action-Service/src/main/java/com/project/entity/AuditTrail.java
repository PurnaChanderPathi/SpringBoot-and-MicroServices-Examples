package com.project.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditTrail {

    private String reviewId;
    private String currentAction;
    private Date inTime;
    private Date outTime;
    private String actionedBy;
}
