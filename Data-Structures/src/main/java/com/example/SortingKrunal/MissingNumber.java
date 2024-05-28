package com.example.SortingKrunal;

public class MissingNumber {
	public static void main(String[] args) {
		int[] nums = {4,0,2,1};
		System.out.println(missingNumber(nums));

    }
	static int missingNumber(int[] nums) {
        int i=0;
        while(i<nums.length) {
           	int correctIndex = nums[i];
        	if(nums[i]< nums.length && nums[i]!=nums[correctIndex]) {
        		int temp = nums[correctIndex];
        		nums[correctIndex] = nums[i];
        		nums[i] = temp;
        	}else {
        		i++;
        	}
        }
         for(int index=0; index<nums.length; index++) {
        	if(nums[index]!=index) {
        		return index;
        	}
        }
        return nums.length;
	}

}
