package com.example.arrays;

public class Max {
	public static void main(String[] args) {
		int[] arr = {1,3,23,9,18}; 
		max(arr);
	}
	
	static void max(int[] arr) {
		int maxVal = arr[0];
		for(int i=1; i<arr.length; i++) {
			if(maxVal < arr[i]) {
				maxVal = arr[i];
			}
		}
		System.out.println(maxVal);
	}
	
}
