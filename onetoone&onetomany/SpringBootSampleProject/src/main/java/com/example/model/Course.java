package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CourseTable")
public class Course {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long courseId;
	
	private String courseName;
	
	private String courseFee;

}
