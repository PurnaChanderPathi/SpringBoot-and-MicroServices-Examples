package com.example.functions;

public class Swap {
	public static void main(String[] args) {
		int a = 10;
		int b = 20;
		
		swap(a, b);
		System.out.println(a+" "+b);
		
		String name = "Pathi";
		changeName(name);
		System.out.println(name);
	}
	
	static void swap(int a, int b) {
		int temp = a;
		a=b;
		b=temp;
		 // this change will only be valid in this function scope only.
		
	}
	
	static void changeName(String name) {
		name = "Purna"; // not changing object creating new object
	}
}
