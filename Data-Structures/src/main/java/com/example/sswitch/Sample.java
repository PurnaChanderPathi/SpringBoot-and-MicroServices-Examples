package com.example.sswitch;

import java.util.Scanner;

public class Sample {
	public static void main(String[] args) {
		System.out.println("Enter Fruit Name");
		Scanner sc = new Scanner(System.in);
		String fruit = sc.next();
		
		switch(fruit) {
		case "Mango":System.out.println("King of the fruits");
		break;
		case "Apple": System.out.println("A Sweet Red Fruit");
		break;
		case "Orange": System.out.println("Round Fruit");
		break;
		case "Grapes": System.out.println("Small fruit");
		break;
		default: System.out.println("please enter a valid fruit");
		}
	}

}
