package com.example.BinarySearch;

import java.util.ArrayList;
import java.util.List;

public class MissingPostiveNumber {
	public static void main(String[] args) {
		int[] arr = {1,13,18};
		System.out.println("result : "+findKthPositive(arr, 17));
	}
    public static int findKthPositive(int[] arr, int k) {
    	int num = 1;
    	int result = 0;

    	List list = new ArrayList();
    	int i = 0;
    	while(i<=arr.length-1) {
    		if(arr[i]==num) {
    			num++;
    			i++;
    		} else {   		
    			list.add(num);
    			num++;
    		}
    	}
    	if(list.size() == 0) {
    		result = arr[arr.length-1]+k
    				;
    	}else {
    		if(k>list.size()) {
        		int p = list.size();
        		int q = arr.length-1;
        		int s = arr[q];
        		for(int j=list.size()-1; j<k; j++) {
        			int ans = s+1;
        			list.add(ans);
        			s++; 			
        		}
    		}

       	result = (int) list.get(k-1);
    	}
        return result ;
    }   
}
