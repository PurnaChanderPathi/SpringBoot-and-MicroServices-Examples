package com.project.entity;

import lombok.Data;

@Data
public class Obligor {

    private String obligorId;
    private String reviewId;
    private String childReviewId;
    private String division;
    private String obligorName;
    private int premId;
    private int cifId;

}
