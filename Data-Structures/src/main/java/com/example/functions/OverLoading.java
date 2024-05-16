package com.example.functions;

import java.util.Arrays;

public class OverLoading {
	public static void main(String[] args) {
		fun("Purna");
		System.out.println(sum(10, 2,3));
		demo("Purna","Chander","pathi");
		//demo(null); // ambiguity error if you don't add parameters
	}
	
	static void  demo(int ...v) {
		System.out.println(Arrays.toString(v));
	}
	
	static void  demo(String ...v) {
		System.out.println(Arrays.toString(v));
	}
	
	static int sum(int a, int b) {
		return a+b;
	}
	
	static int sum(int a, int b, int c) {
		return a+b+c;
	}
	
	static void fun(int a) {
		System.out.println("first one");
		System.out.println(a);
	}
	
	static void fun(String name) {
		System.out.println("Second one");
		System.out.println(name);
	}
}
