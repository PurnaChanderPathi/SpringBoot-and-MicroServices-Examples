package com.example.SortingKrunal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InterSectionOfTwoArrays {
	public static void main(String[] args) {
		int[] nums1 = {1,2,2,1};
		int[] nums2 = {2,2};
		System.out.println(Arrays.toString(intersection(nums1, nums2)));
	}
	
    public static int[] intersection(int[] nums1, int[] nums2) {
        List<Integer> list=new ArrayList();
        for(int i=0;i<nums1.length;i++)
        {
            for(int j=0;j<nums2.length;j++)
            {
                if(nums1[i]==nums2[j])
                {
                    if(!list.contains(nums1[i]))
                    {
                        list.add(nums1[i]);
                    }

                }
            }
        }
        int[] a=new int[list.size()];
        for(int k=0; k<list.size(); k++){
            a[k]=list.get(k);
        }
        return a;
    }
	
}
