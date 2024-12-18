package com.project.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AdminConfig {

    private int seqNo;
    private String groupName;
    private String division;
        private Date createOn;
        private String Spoc;
}
