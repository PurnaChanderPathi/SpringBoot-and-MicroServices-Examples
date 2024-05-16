package com.example.functions;

import java.util.Arrays;

public class VarArgs {
	public static void main(String[] args) {
		fun(1,2,3,4,5,6,7,8,9);
		multiple(1, 2, "purna","chander","pathi");
	}
	
	static void multiple(int a, int b, String ...v) {
		System.out.println(a+" "+b+" "+Arrays.toString(v));
	}
	
	//(...a) variable length argument
	static void fun(int ...a) { 
		System.out.println(Arrays.toString(a));
		System.out.println(a.length);
	}
}
