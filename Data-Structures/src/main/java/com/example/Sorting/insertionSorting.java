package com.example.Sorting;

import java.util.Arrays;

public class insertionSorting {

	public static void main(String[] args) {
		 System.out.println("Try programiz.pro");
	        int[] a= {2,5,9,4,6,8};
	        
	        for(int i=0; i<a.length-1; i++){
	            for(int j=i+1; j>0; j--){
	                if(a[j]<a[j-1]){
	                    int temp = a[j-1];
	                    a[j-1]=a[j];
	                    a[j]=temp;
	                }else{
	                    break;
	                }
	            }
	        }
	        System.out.println(Arrays.toString(a));
	}
}
