package com.example.linearSearch;

public class FindEvenNumber {
	public static void main(String[] args) {
		int[] nums = {18,124,9,1764,98,1};
		System.out.println(findNumber(nums));
		System.out.println(digits2(0));
	}
	
	static int findNumber(int[] nums) {
		int count = 0;
		for(int num: nums) {
			if(even(num)) {
				count++;
			}
		}
		return count;
	}
	// function to check whether a number contains even digit or not
	 static boolean even(int num) {
		// TODO Auto-generated method stub
		int numberOfDigits = digits(num);
		return numberOfDigits % 2 == 0;
	}
	 
	 static int digits2(int num) {
		 if(num < 0) {
				num = num * -1;
			}
			if(num == 0) {
				return 1;
			}
		 return (int)(Math.log10(num) + 1);
	 }
	
	static int digits(int num) {
		if(num < 0) {
			num = num * -1;
		}
		if(num == 0) {
			return 1;
		}
		int count = 0;
		while(num > 0) {
			count++;
			num = num /10;
		}
		return count;

	}
}
