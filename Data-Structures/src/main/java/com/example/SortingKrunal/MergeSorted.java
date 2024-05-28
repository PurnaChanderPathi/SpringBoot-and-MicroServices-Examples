package com.example.SortingKrunal;

import java.util.Arrays;

public class MergeSorted {
	public static void main(String[] args) {
		int[] nums1= {-1,0,0,3,3,3,0,0,0};
		int m=6;
		int[] nums2= {1,2,2};
		int n=3;
		 
    	int[] list = new int[n+m];
    	int x=0;
    	int i=0;
    	while(i<m+n) {
        	if(i<m+n && nums1[i]==0 && x<=nums2.length-1) {
    		nums1[i]=nums2[x];
    		i++;
    		x++;
    	}else {
    		i++;
    	}
    	}
    	for(int j=0; j<nums1.length-1; j++) {
    		for(int p=j+1; p>0; p--) {
    			if(nums1[p]<nums1[p-1]) {
    				int temp = nums1[p-1];
    				nums1[p-1]=nums1[p];
    				nums1[p]=temp;
    			}else {
    				break;
    		}
    		}
    	}
    	System.out.println(Arrays.toString(nums1));
	}
}
