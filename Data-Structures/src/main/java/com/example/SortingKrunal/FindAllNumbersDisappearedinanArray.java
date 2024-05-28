package com.example.SortingKrunal;

import java.util.ArrayList;
import java.util.List;

/*
 * Given an array nums of n integers where nums[i] is in the range [1, n], 
 * return an array of all the integers in the range [1, n] that do not appear in nums.
 * 
 * Example 1:

Input: nums = [4,3,2,7,8,2,3,1]
Output: [5,6]
Example 2:

Input: nums = [1,1]
Output: [2]
 */
public class FindAllNumbersDisappearedinanArray {
	public static void main(String[] args) {
		int[] nums= {1,2,1,2};
		List<Integer> result = findDisappearedNumber(nums);
		System.out.println(result);
	}

	public static List<Integer> findDisappearedNumber(int[] nums){
		int i=0;
		while(i<nums.length) {
			int correctIndex = nums[i]-1;
			if(nums[i]!=nums[correctIndex]) {
				int temp = nums[correctIndex];
				nums[correctIndex] = nums[i];
				nums[i]=temp;
			}else {
				i++;
			}

		}
		List<Integer> list = new ArrayList<>();
		for(int index=0; index<nums.length; index++) {
			if(nums[index]!=index+1) {
				list.add(index+1);
			}
		}
		return list;
	}
}
