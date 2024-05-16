package com.example.Sorting;

public class incrementSorting {
	public static void main(String[] args) {
        int count =1;
        int[] a={2,3,6,9,8,10,12,15,18,19};
        for(int i=0; i<a.length; i=i+count){
           System.out.println(a[i]); 
            count++;
        }
	}

}
