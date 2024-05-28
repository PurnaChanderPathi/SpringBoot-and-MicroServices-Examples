package com.example.BinarySearch;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSumsArrayisSorted {
	public static void main(String[] args) {
		int[] input = {5,25,75};
		int target = 100;
		System.out.println(Arrays.toString(twoSum(input, target)));
	}
	
	// my method
//    static int[] twoSum(int[] numbers, int target) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
//    		int [] output = new int[2]; 
//    	for(int i=0; i<numbers.length; i++) {
//    		for(int j=i+1; j<numbers.length; j++)
//    		if(numbers[i]+numbers[j]==target) {
//    			output[0]=i+1;
//    			output[1]=j+1;
//    			break;
//    		}
//    	}    	
//    	return output;
//    }
	
	// CGP method
    public static int[] twoSum(int[] numbers, int target) {
        Map<Integer,Integer> map=new HashMap<>();
        int[] output = new int[2];
        int com=0;
        for(int i=0;i<numbers.length;i++)
        {
          com=target-numbers[i];
        if(map.containsKey(com))
        {
           output[0]=map.get(com) + 1;
           output[1]=i+1;
        }
        map.put(numbers[i], i);
        }
    	return output;
    }
}
