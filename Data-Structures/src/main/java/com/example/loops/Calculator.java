package com.example.loops;

import java.util.Scanner;

public class Calculator {
	public static void main(String[] args) {

	Scanner sc = new Scanner(System.in);
	// take input user till user press X or x
	int ans = 0;
	while (true) {
		// take the operator as input
		System.out.println("Select Operator");
		char op = sc.next().trim().charAt(0);
		System.out.println();
		if(op == '+' || op == '-' || op == '*' || op == '/' || op == '%') {
			// input two number
			System.out.println("Enter two numbers");
			int a = sc.nextInt();
			int b = sc.nextInt();
			
			if(op == '+') {
				ans = a + b;
			}
			if(op == '-') {
				ans = a - b;
			}
			if(op == '*') {
				ans = a * b;
			}
			if(op == '/') {
				if(b != 0) {					
				ans = a / b;
				}else {
					System.out.println("cannot be Devided by Zero");
				}
			}
			if(op == '%') {				
				ans = a % b;
			}
		}else if(op == 'x' || op == 'X') {
			break;
		}else {
			System.out.println("invalid Operator");
		}
		System.out.println(ans);
	} 
}
}
