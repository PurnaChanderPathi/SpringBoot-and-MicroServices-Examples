package com.example.test;

public class ContinuousSubarraySum {
	public static void main(String[] args) {
		int[] nums = {23,2,4,6,7};
		int k = 6;
		System.out.println(checkSubarraySum(nums, k));
	}
    public static boolean checkSubarraySum(int[] nums, int k) {
        int num = 0;
        for(int i=0; i<nums.length;i++){
            num = num + nums[i];
        }
   	 int result = num/k;
        for(int j=0; j<nums.length;j++){
            if(num/k==nums[j]){
                return true;
            }
        }
        return false;
    }
}
