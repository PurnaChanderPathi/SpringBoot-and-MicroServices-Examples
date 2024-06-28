package com.example.test;

import java.util.Arrays;

public class MinimumIncrementtoMakeArrayUnique {
	public static void main(String[] args) {
		int[] nums = {3,2,1,2,1,7};
		System.out.print(minIncrementForUnique(nums));
	}
	
    public static int minIncrementForUnique(int[] nums) {
        Arrays.sort(nums);
        int count = 0;
            int i=0;
            while(i<nums.length-1){
            	if(nums[i]>=nums[i+1]) {
            		nums[i+1]=nums[i+1]+1;
            		count++;
            	}
            	if(nums[i]<nums[i+1]) {
            		i++;
            	}
            }
        return count;
    }
}
