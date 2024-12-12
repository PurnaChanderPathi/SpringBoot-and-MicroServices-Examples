package com.project.Dto;

import com.project.entity.Obligor;
import lombok.Data;

@Data
public class webobligor {
    private Obligor result;
    private String status;
    private String message;

}
