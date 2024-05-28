package com.example.SortingKrunal;

import java.util.Arrays;

public class SelectionSort {
	public static void main(String[] args) {
		int arr[] = {-1,0,5,4,3,2,1};
		//selection(arr);
		selectionSort(arr);
		System.out.println(Arrays.toString(arr));		
	}
	
	static void selection(int[] arr) {
		for(int i=0; i<arr.length; i++) {
			// find the max item in remaining array and swap with correct index
			int last = arr.length-i-1;
			int maxIndex = getMaxIndex(arr,0, last); 
			swap(arr, maxIndex, last);
		}
	}

	private static int getMaxIndex(int[] arr, int start, int end) {
		int max = start;
		for(int i=start; i<=end; i++) {
			if(arr[max] < arr[i]) {
				max = i;
			}
		}
		return max;
	}	
	static void swap(int[] arr, int start, int end) {
		int temp = arr[start];
		arr[start] = arr[end];
		arr[end] = temp;
	}	
	
	// method 2
	static void selectionSort(int[] arr) {
		for(int i=0; i<arr.length; i++) {
			int last = arr.length-i-1;
			int start = 0;
			int max = start;
			for(int j=start; j<=last; j++) {
				if(arr[max]<arr[j]) {
					max = j;
				}
			}	
				int temp = arr[max];
				arr[max] = arr[last];
				arr[last] = temp;
		}
	}
}

