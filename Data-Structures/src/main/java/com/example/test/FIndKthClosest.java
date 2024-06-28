package com.example.test;

import java.util.ArrayList;
import java.util.List;

public class FIndKthClosest {

	public static void main(String[] args) {
		int[] arr = {1,1,1,10,10,10};
		int k = 1, x = 3;
		System.out.println(findClosestElements(arr, k, x));
	}
	public static List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> result = new ArrayList();
        int max=Integer.MAX_VALUE;
    	int min=Integer.MIN_VALUE;      
        for(int i=0; i<arr.length; i++){
            int n=arr[i];
            
           
                result.add(n);

                if(n<=x && result.size()==k){
                for(int j=0;j<result.size();j++){
                    int a=result.get(j);
                    if(Math.abs(a-x) > Math.abs(n-x)){
                        result.remove(a);
                        result.add(n);
                        break;
                    }
                }
            }
        }
        return result;
    }
}
