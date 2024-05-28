/*
 * First Missing Positive
 * 
 * Given an unsorted integer array nums. Return the smallest positive integer that is not present in nums.

You must implement an algorithm that runs in O(n) time and uses O(1) auxiliary space.

Example 1:

Input: nums = [1,2,0]
Output: 3
Explanation: The numbers in the range [1,2] are all in the array.
Example 2:

Input: nums = [3,4,-1,1]
Output: 2
Explanation: 1 is in the array but 2 is missing.
Example 3:

Input: nums = [7,8,9,11,12]
Output: 1
Explanation: The smallest positive integer 1 is missing.
 */

package com.example.SortingKrunal;

import java.util.Arrays;

public class FirstMissingPositive {
	public static void main(String[] args) {
        int[] nums = {3,4,-1,1}; 
        System.out.println(firstMissingPositive(nums));
	}
    public static int firstMissingPositive(int[] nums) {   	
    	int k=0;
    	while(k<nums.length) {
    		int correctIndex = nums[k] - 1;
    		if(nums[k] > 0 && nums[k]<=nums.length && nums[k]!=nums[correctIndex] ) {
    			int temp = nums[k];
    			nums[k] = nums[correctIndex];
    			nums[correctIndex]=temp;
    		}else {
    			k++;
    		}
    	}
    	
    	for(int index=0; index<nums.length; index++) {
    		if(nums[index]!=index + 1) {
    			return index+1;
    		}
    	}
    	
    	return nums.length+1;
    }
}
