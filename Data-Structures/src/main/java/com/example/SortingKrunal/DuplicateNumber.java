package com.example.SortingKrunal;
/*
 * Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.

There is only one repeated number in nums, return this repeated number.

You must solve the problem without modifying the array nums and uses only constant extra space

Example 1:

Input: nums = [1,3,4,2,2]
Output: 2
Example 2:

Input: nums = [3,1,3,4,2]
Output: 3
Example 3:

Input: nums = [3,3,3,3,3]
Output: 3
 */

public class DuplicateNumber {
	public static void main(String[] args) {
		int[] nums = {3,3,3,3,3};
		System.out.println(findDuplicate(nums));
		System.out.println(findDuplicates(nums));
	}
    public static int findDuplicate(int[] nums) {
    	int i=0;
    	while(i<nums.length) {
    		int correntIndex = nums[i]-1;
    		if(nums[i]!=nums[correntIndex]) {
    			int temp = nums[correntIndex];
    			nums[correntIndex] = nums[i];
    			nums[i]=temp;
    		}else {
    			i++;
    		}
    	}
        return nums[nums.length-1];
    }
    
    //kunal method
    public static int findDuplicates(int[] nums) {
    	int i=0;
    	while(i<nums.length) {
    		if(nums[i]!=i+1) {
    			int correctIndex =nums[i]-1;
    			if(nums[i]!=nums[correctIndex]) {
        			int temp = nums[correctIndex];
        			nums[correctIndex] = nums[i];
        			nums[i]=temp;
    			}else {
    				return nums[i];
    			}
    		}else {
    			i++;
    		}
    	}
    	return -1;
    }
}
