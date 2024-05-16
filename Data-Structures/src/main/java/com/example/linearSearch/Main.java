package com.example.linearSearch;

public class Main {
	public static void main(String[] args) {
		int[] arr = {12,45,67,23,89,48,89,34,-5,-98,-45,56};
		int target = 34;
		int ans = linearSearch(arr, target);
		System.out.println(ans);
		int answ = linearSearch1(arr, target);
		System.out.println(answ);
		boolean answe = linearSearch2(arr, target);
		System.out.println(answe);
		
	}
	
	// search in the array: return the index if item found
	// otherwise if item not found return -1
	static int linearSearch(int[] arr, int target) {
		if(arr.length == 0) {
			return -1;
		}
		
		// run forloop
		for(int i=0; i<arr.length; i++) {
			//check for element in every index
			if(arr[i]==target) {
				return i;
			}
		}
		// this statement will execute if none of the return statements above are executed
		return -1;
	}
	
	static int linearSearch1(int[] arr, int target) {
		if(arr.length == 0) {
			return -1;
		}
		
		// run enhanced for loop
		for(int element : arr) {
			//check for element in every index
			if(element==target) {
				return element;
			}
		}
		// this statement will execute if none of the return statements above are executed
		return -1;
	}
	
	static boolean linearSearch2(int[] arr, int target) {
		if(arr.length == 0) {
			return false;
		}
		
		// run enhanced for loop
		for(int element : arr) {
			//check for element in every index
			if(element==target) {
				return true;
			}
		}
		// this statement will execute if none of the return statements above are executed
		return false;
	}
	
	
}
