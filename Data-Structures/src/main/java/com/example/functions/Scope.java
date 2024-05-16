package com.example.functions;

public class Scope {
	public static void main(String[] args) {
		int a = 10;
		int b = 20;
		
		
		/*
		 * some thing we have initialized in the function or methods can be used in block inside method
		 * but somw thing we have initialized in the blocks we cannot use them outside the blocks of same function or method
		 */
		
		{
			//int a = 70; // error already initialized outside the block in same method
		//	a = 40; // values initialized in function can be used in blocks and reassign the original reference variable to some other values 
		//	System.out.println("changed a : "+a);
		//	int c = 50; // values initialized in this block, will remain in block
			
		}
		//System.out.println(c); // can not use outside the block
		random(a);
		System.out.println(b);
		
		for(int i=0; i<1; i++) {
			a = 1000;
			System.out.println(a);
		}
		System.out.println(a);

	}
	
	static void random(int marks) {
		int num = 30;
		System.out.println(num);
		System.out.println(marks);
	}
}
