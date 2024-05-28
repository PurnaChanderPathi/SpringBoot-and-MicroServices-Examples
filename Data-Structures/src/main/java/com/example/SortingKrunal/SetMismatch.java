/*
 * Set MisMatch
 * 
 * You have a set of integers s, which originally contains all the numbers from 1 to n. Unfortunately, due to some error, one of the numbers in s got duplicated to another number in the set, which results in repetition of one number and loss of another number.

You are given an integer array nums representing the data status of this set after the error.

Find the number that occurs twice and the number that is missing and return them in the form of an array.

Example 1:

Input: nums = [1,2,2,4]
Output: [2,3]
Example 2:

Input: nums = [1,1]
Output: [1,2]
 */

package com.example.SortingKrunal;

import java.util.Arrays;

public class SetMismatch {
	public static void main(String[] args) {
		int[] nums = {1,2,4,4};
		System.out.println(Arrays.toString(findErrorNums(nums)));
	}
    public static int[] findErrorNums(int[] nums) {        
    	int i=0;
    	while(i<nums.length) {
    		int corrrectIndex = nums[i]-1;
    		if(nums[i]!=nums[corrrectIndex]) {
    			int temp = nums[corrrectIndex];
    			nums[corrrectIndex]= nums[i];
    			nums[i]=temp;
    		}else {
    			i++;
    		}
    	}
    	int[] list = new int[2];
    	
    	// Purna
//    	for(int index=0; index<nums.length; index++) {
//    		if(nums[index]>= index+2) {
//    			list[0]=nums[index];
//    			list[1]=index+1;
//    			break;
//    		}
//    		if(nums[index]!=index+1) {
//    			list[0]=nums[index];
//    			list[1]=index+1;
//    		}
//    	}
    	
    	// kunal
    	for(int index=0; index<nums.length; index++) {
    		if(nums[index] != index+1) {
    			return new int[] {nums[index],index+1};
    		}
    	}
    	
    	return new int[] {-1,-1};
    }
}
