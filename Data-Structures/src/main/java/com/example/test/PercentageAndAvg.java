package com.example.test;

import java.util.Arrays;
import java.util.List;

public class PercentageAndAvg {

//	public static void main(String[] args) {
//		
//		List<Student> students = Arrays.asList(
//				new Student("Virat", 60, 70, 80),
//				new Student("Rohit", 60, 85, 78)
//				);
//		
//		students.forEach(student -> {
//			double percentage = calculatePercentage(student);
//			System.out.println(student.getName()+" get percentage : "+percentage );
//		});
//	}
//	
//	private static double calculatePercentage(Student student) {
//		double totalscore = student.getEnglish()+student.getMaths()+student.getScience();
//		double totalSubject = 3;
//		return (totalscore * 100.0)/(totalSubject * 100);
//	}
	
	public static void main(String[] args) {
		
		List<Student> students = Arrays.asList(
				new Student("Purna", 45, 55, 35),
				new Student("ajeeth", 45, 75, 65)
				);
		
		students.forEach(student -> {
			double percentage = calculatePercentage(student);
			System.out.println(student.getName()+" with Avg :"+percentage);
		});
	}

	private static double calculatePercentage(Student student) {
		double totalScore = student.getEnglish()+student.getMaths()+student.getScience();
		int totalSubject = 3;
		return (totalScore * 100.0)/(totalSubject * 100);
	}

	static class Student{
		private String name;
		private double english;
		private double science;
		private double maths;
		public Student(String name, double english, double science, double maths) {
			super();
			this.name = name;
			this.english = english;
			this.science = science;
			this.maths = maths;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getEnglish() {
			return english;
		}
		public void setEnglish(double english) {
			this.english = english;
		}
		public double getScience() {
			return science;
		}
		public void setScience(double science) {
			this.science = science;
		}
		public double getMaths() {
			return maths;
		}
		public void setMaths(double maths) {
			this.maths = maths;
		}
		
		
	}
}
