package com.example.loops;

import java.util.Scanner;

public class Largest {
	public static void main(String[] args) {
		System.out.println("Enter numbers");
		Scanner scr = new Scanner(System.in);
		int a = scr.nextInt();
		int b = scr.nextInt();
		int c= scr.nextInt();
		
	//	method 1
//		int max = a;
//		if(b > max) {
//			max = b;
//		}
//		if(c > max) {
//			max = c;
//		}
		
		// method 2
//		int max =0;
//		if(a > b) {
//			max = b;
//		}else {
//			max = a;
//		}
//		if(c > max) {
//			max =c;
//		}
		
		// method 3
		int max = Math.max(c, Math.max(a, b));

		System.out.println("largest number :"+max);
	}
}
