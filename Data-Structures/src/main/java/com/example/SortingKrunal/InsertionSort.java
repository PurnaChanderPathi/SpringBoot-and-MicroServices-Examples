package com.example.SortingKrunal;

import java.util.Arrays;

public class InsertionSort {
	public static void main(String[] args) {
		int arr[] = {-1,0,5,4,3,2,1};
		insertionSort(arr);
		System.out.println(Arrays.toString(arr));		
	}
	
	static void insertionSort(int[] nums) {
//		for(int i=0; i<arr.length-1;i++) {
//			for(int j=i+1; j>0; j--) {
//				if(arr[j]<arr[j-1]) {
//					int temp = arr[j-1];
//					arr[j-1] = arr[j];
//					arr[j] = temp;
//				}else {
//					break;
//				}
//			}
//		}
		 int t=0,k=nums.length-1;
	       for(int i=0;i<nums.length;){
	         if(nums[i]==0&&i!=t){
	            int temp=nums[t];
	            nums[t]=nums[i];
	            nums[i]=temp;
	            t++;
	         }
	         else if(k>i&&nums[i]==2){
	            int temp=nums[k];
	            nums[k]=nums[i];
	            nums[i]=temp;
	            k--;
	         }
	         else{
	            i++;
	         }
	       } 
	}
}
