package com.example.test;

public class SkipNumbers {
	public static void main(String[] args) {
		int[] nums = {1,2,3,4,5,6,7,8,9,10};
		
		// Output :- [1,3,6,10]
//		int j=1;
//		for(int i=0; i<nums.length; i=j+i) {	
//			System.out.print(nums[i]+" ");
//			j++;
//		}
		
		// Output :- [2,4,6,8,10]
//		int j=2;
//		for(int i=1; i<nums.length;i=i+j) {
//			System.out.print(nums[i]+" ");
//		}
		
		// Output :- [1,3,5,7,9]
		int j=2;
		for(int i=0; i<nums.length;i=i+j) {
			System.out.print(nums[i]+" ");
		}
	}
}
