package com.example.BinarySearch;

public class SearchInsertPosition {
	public static void main(String[] args) {
		int[] nums = {1};
		System.out.println(searchInsert(nums, 2));
	}
	
    public static int searchInsert(int[] nums, int target) {
        int i=0;
//        if(target == 0) return 0;
//        if(target == 1) return 0;
        if(nums.length-1<1) {
            if(nums[i]>target){
                return i;
            }else if(nums[i]<target){
                return i+1;
            }else {
            	return i;
            }
        }
        while(i<nums.length){
            if(nums[i]==target){
                return i;
            }
            if(nums[i]>target){
                return i;
            }
            int j = nums.length-1;
            if(nums[j]<target) {
            	return j+1;
            }
            i++;
        }
        return -1;
    }
}
