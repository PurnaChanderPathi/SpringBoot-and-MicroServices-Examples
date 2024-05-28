package com.example.SortingKrunal;

public class ThreeSumClosest {
	public static void main(String[] args) {
		int[] nums = {-1,2,1,-4};
		int target = 1;
		System.out.println(threeSumClosest(nums, target));
	}

    public static int threeSumClosest(int[] nums, int target) {
    	        int n=nums.length;
    	        int max=Integer.MAX_VALUE;
    	        int min=Integer.MIN_VALUE;
    	        for(int i=0;i<n-2;i++){
    	            int v1=nums[i];
    	            for(int j=i+1;j<n-1;j++){
    	                int v2=nums[j];
    	                for(int k=j+1;k<n;k++){
    	                    int v3=nums[k]; 
    	                    int sum=v1+v2+v3;
    	                   if(sum==target){
    	                    return sum;
    	                   }else if(sum>target && max>sum){
    	                         max=sum;
    	                   }else if(sum<target && min<sum){
    	                       min=sum;
    	                   }
    	                }
    	            }
    	        }
    	        if(min==Integer.MIN_VALUE  && max==Integer.MAX_VALUE) {
    	    	   return min;
    	        }else if(min==Integer.MIN_VALUE ) {
    	    	   return max;
    	        }else if(max==Integer.MAX_VALUE) {
    	    	   return min;
    	        }
    	        if(target-min > max-target){
    	            return max;
    	        }else{
    	            return min;
    	        }
    }
}
