package com.example.sswitch;

import java.util.Scanner;

public class NestedSwitch {
	public static void main(String[] args) {
		System.out.println("Entrer case number");
		Scanner sc = new Scanner(System.in);
		int empId = sc.nextInt();
		String department = sc.next();
		
		switch (empId) {
		case 1: System.out.println("Pathi");
		break;
		case 2: System.out.println("Purna");
		break;
		case 3:
			System.out.println("Emp number 3 ");
			switch(department) {
			case "IT": System.out.println("IT Department");
			break;
			case "Management": System.out.println("Management Department");
			break;
			default:
				System.out.println("no Department entered ");
			}
			break;
			default:
				System.out.println("Enter Correct Empid");
		}
	}
}
