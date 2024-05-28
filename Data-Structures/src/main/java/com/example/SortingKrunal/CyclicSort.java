package com.example.SortingKrunal;

import java.util.Arrays;

public class CyclicSort {
	public static void main(String[] args) {
		int[] arr = {1,3,5,2,4};
		CyclicSort(arr);
		System.out.println(Arrays.toString(arr));
	}
	
	static void CyclicSort(int[] arr) {
		int i = 0;
		while(i<arr.length) {
			int correctIndex = arr[i]-1;
			if(arr[i]!=arr[correctIndex]) {
				int temp = arr[correctIndex];
				arr[correctIndex] = arr[i];
				arr[i] = temp;
			}else {
				i++;
			}
		}
	}
}
