package com.example.test;

import java.util.ArrayList;
import java.util.List;

public class FindKClosestElements {
	public static void main(String[] args) {
		int[] arr = {1,1,1,10,10,10};
				int k = 1; 
				int  x = 9;
				System.out.println(findClosestElements(arr, k, x));
	}
    public static List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> result = new ArrayList();
        for(int i=0; i<k; i++){
            result.add(arr[i]);
        }
        return result;
    }
	
}
