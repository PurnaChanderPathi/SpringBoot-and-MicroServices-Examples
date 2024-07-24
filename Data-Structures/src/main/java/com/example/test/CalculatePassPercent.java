package com.example.test;

import java.util.Arrays;
import java.util.List;

public class CalculatePassPercent {
	
	public static void main(String[] args) {
		
		List<Student> students = Arrays.asList(
				new Student("Purna", 45),
				new Student("Ajeeth", 55),
				new Student("pavan", 65)
				);
		
		double percentage = calculatePercentage(students, 50);
		System.out.println( "Percentage : "+percentage);
	}	

	private static double calculatePercentage(List<Student> students, int fiftymarks) {
		long totalStudents = students.size();
		long passedStudents = students.stream().filter(student -> student.getMarks()>fiftymarks).count();
		
		return ((double)passedStudents/totalStudents)*100;
	}

	static class Student{
			
		private String name;
		private int marks;
		
		public Student(String name, int marks) {
			super();
			this.name = name;
			this.marks = marks;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getMarks() {
			return marks;
		}

		public void setMarks(int marks) {
			this.marks = marks;
		}		
	}
}
