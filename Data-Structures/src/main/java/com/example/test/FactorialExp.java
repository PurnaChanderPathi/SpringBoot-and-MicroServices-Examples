package com.example.test;

public class FactorialExp {
	public static void main(String[] args) {
		System.out.println(factorial(5));
	}
	public static int factorial(int c) {
//		if(c==1) return 1;
//		return c*factorial(c-1);
		int result =1;
		int i=1;
		while(i<=c) {
			result*=i;
			i++;
		}
		return result;
		
	}
}
