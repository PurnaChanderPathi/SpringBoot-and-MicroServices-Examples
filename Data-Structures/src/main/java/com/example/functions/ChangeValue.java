package com.example.functions;

import java.util.Arrays;

public class ChangeValue {
	public static void main(String[] args) {
		
		int[] a = {1,2,3,4,5,6};
		change(a);
		System.out.println(Arrays.toString(a));
	}
	
	static void change(int[] num) {
		num[0]=9; // if you make change to the object via this reference variable, same object will be changed
	}
}
