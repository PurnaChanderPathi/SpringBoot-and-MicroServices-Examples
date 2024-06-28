package com.example.test;

public class SkipNumber {
public static void main(String[] args) {
	int[] nums = {1,2,3,4,5,6,7,8,9,10,11};
	int k=0;
	for(int i=0; i<nums.length;i++) {
		int count=0;
		while(count!=k && i<nums.length) {
			int s=nums[i++];
			int n=i;
			System.out.print(s+" ");
			count++;
		}

		k++;
	}
	
//	int j=1;
//	for(int i=0; i<nums.length; i=i+j) {
//		System.out.print(nums[i]+" ");
//		j++;
//	}
}
}
