package com.example.String;

public class Pattern6 {
	public static void main(String[] args) {
		pattern6(5);
	}
	
	static void pattern6(int n) {
		int m=n-1;
		for(int row=0; row<n; row++) {
			for(int s=0; s<=m; s++) {
				System.out.print(" ");
			}
			for(int col=0; col<row; col++) {
				System.out.print("*");
			}
			System.out.println();
			m--;
		}
	}
}
