package com.example.BinarySearch;

public class OrderAgnosticBinarySearch {
	public static void main(String[] args) {
		int[] arr = {19,16,13,9,6,4,1,-3,-5,-9};
		int target = 13;
		System.out.println(orderAgnosticBS(arr, target));
	}
	static int orderAgnosticBS(int[] arr, int target) {
		int start = 0;
		int end = arr.length-1;
		
		// find whether the araay sorted in Asc or Desc order
		boolean isAsc = arr[start]<arr[end];
		
		while(start <= end) {
			//find middle index
			// int mis = (start = end)/2 might be possible (start + end) exeed the length of int
			int mid = start + (end - start)/2;
			
			if(arr[mid] == target) {
				return mid;
			}
			
			if(isAsc) {
				if(target < arr[mid]) {
					end = mid -1;
				}else if(target > arr[mid]) {
					start = start +1;
				}
			}else {
				if(target > arr[mid]) {
					end = mid -1;
				}else if(target < arr[mid]) {
					start = start +1;
				}
			}
			

		}return -1;
	}
}
