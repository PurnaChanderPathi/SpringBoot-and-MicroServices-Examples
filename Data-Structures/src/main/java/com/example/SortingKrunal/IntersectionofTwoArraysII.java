package com.example.SortingKrunal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntersectionofTwoArraysII {
	public static void main(String[] args) {
		int[] nums1= {4,9,5};
		int[] nums2= {9,4,9,8,4};
		System.out.println(Arrays.toString(intersect(nums1, nums2)));
	}
    public static int[] intersect(int[] nums1, int[] nums2) {
        List<Integer> list = new ArrayList<>();
      for(int i=0; i<nums1.length; i++){
        for(int j=0; j<nums2.length; j++){
            if(nums1[i]==nums2[j]){
                list.add(nums1[i]);
                // change the matching element in num2 array
                nums2[j] = Integer.MIN_VALUE;
                break;
            }
        }
      }  

    int[] result = new int[list.size()];
      for(int k=0; k<list.size(); k++){
        result[k]=list.get(k);
      }
      return result;
    }
}
