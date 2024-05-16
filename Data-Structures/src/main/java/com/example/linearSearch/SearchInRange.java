package com.example.linearSearch;

public class SearchInRange {
	public static void main(String[] args) {
		int[] arr = {18, 12, -7, 3, 14, 28};
		int target = 14;	
		System.out.println(linearSearch(arr, target, 1, 4));
	}
	
	static int linearSearch(int[] arr, int target, int start, int end) {
		if(arr.length == 0) {
			return -1;
		}
		
		// run forloop
		for(int i=start; i<end; i++) {
			//check for element in every index
			if(arr[i]==target) {
				return i;
			}
		}
		// this statement will execute if none of the return statements above are executed
		return -1;
	}
}
