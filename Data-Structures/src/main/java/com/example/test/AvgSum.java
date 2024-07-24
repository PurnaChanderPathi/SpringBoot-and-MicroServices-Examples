package com.example.test;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class AvgSum {
		
	public static void main(String[] args) {
		
		List<Sum> sums = Arrays.asList(
					new Sum("Purna", 90),
					new Sum("ajeeth", 60),
					new Sum("shiva", 55)
				);
		
//		// avg
		System.out.println("Avg");
		System.out.println("****************************");
		OptionalDouble avg = sums.stream().mapToDouble(Sum::getMarks).average();
		
		if(avg.isPresent()) {
			System.out.println("Sum of avg : "+avg.getAsDouble());
		}else {
			System.out.println("sum is empty");
		}
		
		// percentage 
		System.out.println("percentage");
		System.out.println("*************************");
		int maxPossiblemarks = 100;
		
		List<String> studentPercentage = sums.stream()
				.map(sum -> {
					double percentage = (double) sum.getMarks() / maxPossiblemarks * 100;
					return sum.getName() + ": " + percentage + "%";
				}).collect(Collectors.toList());
		
		studentPercentage.forEach(System.out::println);
	}
	
	public static class Sum{
		
		private String name;
		private double marks;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getMarks() {
			return marks;
		}
		public void setMarks(double marks) {
			this.marks = marks;
		}
		public Sum(String name, double marks) {
			super();
			this.name = name;
			this.marks = marks;
		}				
	}

}
