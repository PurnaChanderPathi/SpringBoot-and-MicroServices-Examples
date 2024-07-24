package com.example.test;

import java.util.Arrays;
import java.util.List;

public class Student12Impl {
	
	public static void main(String[] args) {
		
        List<Student12> students = Arrays.asList(
                new Student12("Alice", 85, 90, 78),
                new Student12("Bob", 75, 80, 85),
                new Student12("Charlie", 95, 70, 65)
        );
        
        students.forEach(student -> {
        	double percentage = calculateStudentPercentage(student);
        	System.out.println(percentage);
        });
	}

	private static double calculateStudentPercentage(Student12 student) {
		int totalScore = student.getEnglish()+student.getMaths()+student.getScience(); 
		int totalSubjects = 3;
		return (totalScore * 100.0)/(totalSubjects * 100);
	}

}
