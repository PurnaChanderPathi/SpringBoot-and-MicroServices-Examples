package com.example.arrays;

import java.util.ArrayList;
import java.util.Scanner;

public class ArrayListExample {
	public static void main(String[] args) {
		//Syntax
		Scanner sc= new Scanner(System.in);
		ArrayList<Integer> list = new ArrayList<>(5);
//		list.add(10);
//		list.add(20);
//		list.add(30);
//		list.add(40);
//		list.add(50);
//		list.add(60);
//		list.add(70);
//		list.add(80);
//		list.add(90);
//		list.add(100);
		//System.out.println(list);
		
		//input
		for(int i=0; i<5; i++) {
			list.add(sc.nextInt());
		}
		
		// get item at index
		for(int i=0; i<5; i++) {
			System.out.println(list.get(i)); //pass index here, list[index] syntax will not work here 
		}
		System.out.println(list);
	}
	
}
