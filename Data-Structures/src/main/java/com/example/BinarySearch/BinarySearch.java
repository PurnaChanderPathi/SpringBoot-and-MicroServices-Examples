package com.example.BinarySearch;

public class BinarySearch {
	public static void main(String[] args) {
		int[] arr = {-19,-10,-5,0,2,8,12,18,23,28,34,39,42};
		int target = 28;
		System.out.println(binarySearch(arr, target));
	}
	
	//return index;
	//return -1 if target does not exist
	static int binarySearch(int[] arr, int target) {
		int start =0;
		int end = arr.length-1;
		
		while(start <= end) {
			//find middle index
			// int mis = (start = end)/2 might be possible (start + end) exeed the length of int
			int mid = start + (end - start)/2;
			
			if(target < arr[mid]) {
				end = mid -1;
			}else if(target > arr[mid]) {
				start = start +1;
			}else {
				// ans found
				return mid;
			}
		}return -1;
	}
}
