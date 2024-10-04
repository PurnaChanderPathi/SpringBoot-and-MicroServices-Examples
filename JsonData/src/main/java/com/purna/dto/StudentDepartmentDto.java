package com.purna.dto;

import lombok.Data;

import java.util.Date;

@Data
public class StudentDepartmentDto {

    private StudentDto student;
    private DepartmentDto department;

    @Data
    public static class StudentDto{
        private String studentName;
        private String gender;
        private Date dateOfBirth;
        private String fatherName;
        private String motherName;
    }

    @Data
    public static class DepartmentDto{
        private String departmentName;
        private String departmentCode;
    }
}
