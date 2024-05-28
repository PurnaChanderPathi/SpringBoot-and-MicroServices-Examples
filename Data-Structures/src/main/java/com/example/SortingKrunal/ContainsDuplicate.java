package com.example.SortingKrunal;

import java.util.Arrays;

public class ContainsDuplicate {
public static void main(String[] args) {
	int[] nums= {1,2,3,4};
	System.out.println(containsDuplicate(nums));
}
public static boolean containsDuplicate(int[] nums) {
//    int i=0;
//    while(i<nums.length){
//        int correctIndex = nums[i]-1;
//        if(nums.length<1 &&nums[i]!=nums[correctIndex]){
//            int temp = nums[correctIndex];
//            nums[correctIndex]=nums[i];
//            nums[i]=temp;
//        }else{
//            i++;
//        }
//    }
	Arrays.sort(nums);
    if(nums.length<=1) {
    	return false;
    }
    int count = 1;
    	for(int k=1; k<nums.length;k++) {
    		if(nums[k]==nums[k-1]) {
    			count++;
    			break;
    		}
    	}
        if(count != 1) {
        	return true;
        }
    

    return false;
}
}
