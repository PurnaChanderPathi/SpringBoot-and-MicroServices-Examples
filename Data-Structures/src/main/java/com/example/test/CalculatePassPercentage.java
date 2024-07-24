package com.example.test;

import java.util.Arrays;
import java.util.List;

public class CalculatePassPercentage {

	public static void main(String[] args) {
		
        List<Student> students = Arrays.asList(
                new Student("Alice", 45),
                new Student("Bob", 55),
                new Student("Charlie", 65),
                new Student("David", 35),
                new Student("Eve", 75)
            );
        
        double percentage = calculatePercentage(students, 50);
        System.out.println(percentage);        
	}

	private static double calculatePercentage(List<Student> students, int halfMarks) {
		long totalStudents = students.size();
		long passedStudents = students.stream().filter(student -> student.getScore()>halfMarks).count();
		
		return ((double)passedStudents/totalStudents)*100;
	}
}
