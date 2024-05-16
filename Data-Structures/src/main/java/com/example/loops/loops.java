package com.example.loops;

import java.util.Iterator;
import java.util.Scanner;

public class loops {

	public static void main(String[] args) {
		
		// For loop
		// syntax
		/* for(initialisation; condition; increment/decrement){
		 *  body
		 * }
		 * 
		 * use for loop when you know the n number to iterations
		 * */
		 
		
//		for(int i=0; i<=5; i++) {
//			System.out.println(i);
//		}
//		System.out.println("enter number to iterate");
//		Scanner scanner = new Scanner(System.in);
//		int n = scanner.nextInt();
//		
//		for(int num =1; num <= n; num++) {
//			//System.out.print(num +" ");
//			System.out.println("Hello world");
//		}
		
		// While loops 
		/*
		 * Syntax
		 * while(condition)
		 * body
		 * }
		 * use while loop when you don't know the n number to iterations
		 */
		
//		int num =1;
//		while(num<=5) {
//			System.out.print(num+" ");
//			num++;
//		}
		
		// do while loop
		/*
		 * do{
		 * 
		 * }while (condition);
		 */
		int n=1;
		do {
			System.out.print(n+" ");
			n++;
		}while(n<=6);
	}
}
