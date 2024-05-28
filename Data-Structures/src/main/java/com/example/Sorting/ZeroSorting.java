package com.example.Sorting;

import java.util.Arrays;

public class ZeroSorting {
	public static void main(String[] args) {
		int[] sort = {1,0,3,0,12,0};
		for(int i=0; i<sort.length; i++) {
			for(int j=i+1; j<sort.length; j++) {
				if(sort[j-1]<sort[j]) {
					int temp = sort[j-1];
					sort[j-1] = sort[j]; 
					sort[j] = temp;
				}
			}
		}
		System.out.println(Arrays.toString(sort));
	}
}
