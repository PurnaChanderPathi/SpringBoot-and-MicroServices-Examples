package com.example.SortingKrunal;

import java.util.Arrays;

public class MergeSortedArray {
	public static void main(String[] args) {
		int[] nums1= {1,2,3,0,0,0};
		int m=3;
		int[] nums2= {2,5,6};
		int n=3;
		 
    	int[] list = new int[n+m];
    	int x=0;
    	int i=0;
    	while(i<m) {
        	if(i<m && nums1[i]!=0) {
    		list[i]=nums1[i];
    		i++;
    		x++;
    	}
    	}
    	i=0;
    	while(i<n) {
        	if(nums2[i]!=0) {
    		list[x]=nums2[i];
    		i++;
    		x++;
    	}
    	}
    	for(int j=0; j<list.length-1; j++) {
    		for(int p=j+1; p>0; p--) {
    			if(list[p]<list[p-1]) {
    				int temp = list[p-1];
    				list[p-1]=list[p];
    				list[p]=temp;
    			}else {
    				break;
    		}
    		}
    	}
    	System.out.println(Arrays.toString(list));
	}
}
