package com.example.linearSearch;

public class FindMinAndMax {
	public static void main(String[] args) {
		int[] arr = {18, 12, -7, 3, 14, 28};
		System.out.println(min(arr));
		System.out.println(max(arr));
	}
	
	static int min(int[] arr) {
		int min = arr[0];
		for(int i=1; i<arr.length; i++) {
			if(min>arr[i]) {
				min=arr[i];
			}
		}
		return min;
	}
	
	static int max(int[] arr) {
		int min = arr[0];
		for(int i=1; i<arr.length; i++) {
			if(min<arr[i]) {
				min=arr[i];
			}
		}
		return min;
	}
}
