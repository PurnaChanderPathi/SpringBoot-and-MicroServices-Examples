package com.project.Dto;

import com.project.entity.ResponseRemediation;
import lombok.Data;

@Data
public class webRes {
    private ResponseRemediation result;
    private String status;
    private String message;
}
