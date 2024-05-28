package com.example.SortingKrunal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Given an integer array nums of length n where all the integers of nums are in the range [1, n] and each integer appears once or twice, return an array of all the integers that appears twice.

You must write an algorithm that runs in O(n) time and uses only constant extra space.

Example 1:

Input: nums = [4,3,2,7,8,2,3,1]
Output: [2,3]
Example 2:

Input: nums = [1,1,2]
Output: [1]
Example 3:

Input: nums = [1]
Output: []
 */

public class FindAllDuplicatesinAnArray {
	public static void main(String[] args) {
	int[] nums= {5,4,6,7,9,3,10,9,5,6};
	System.err.println(findDuplicates(nums));
}
    public static List<Integer> findDuplicates(int[] nums) {
        int i=0;
		while(i<nums.length) {
			int correctIndex = nums[i]-1;
			if(nums[i]!=nums[correctIndex]) {
				int temp = nums[correctIndex];
				nums[correctIndex] = nums[i];
				nums[i] = temp;
			}else {
				i++;
			}
		}
        List<Integer> list = new ArrayList<>();
		for(int index=0; index<nums.length; index++) {
			if(nums[index]!=index+1) {
				list.add(nums[index]);
			}
		}
        return list;
    }
}
