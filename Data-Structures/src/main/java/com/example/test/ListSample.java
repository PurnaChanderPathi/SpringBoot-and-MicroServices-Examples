package com.example.test;

import java.util.Arrays;

public class ListSample {
	public static void main(String[] args) {
		
		int[] a = {1,5,8};
		int[] b = {2,4,9};
		int[] c=new int[a.length+b.length];
		int x=0;
		for(int i=0; i<a.length; i++) {
				if(a[i]<b[i]){
					c[x]=a[i];
					x++;
					c[x]=b[i];
					x++;
				}else {
					c[x]=b[i];
					x++;
					c[x]=a[i];
					x++;					
			}

		}
		System.out.println(Arrays.toString(c));	
	}
}
