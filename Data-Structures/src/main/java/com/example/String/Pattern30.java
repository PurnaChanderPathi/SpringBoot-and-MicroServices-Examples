package com.example.String;

public class Pattern30 {
	public static void main(String[] args) {
		pattern39(6);
	}
	
	static void pattern39(int n) {
		for(int row=0; row<n; row++) {
			for(int s=0; s<n-row; s++) {
				System.out.print(" ");
			}
			for(int col=row; col>=1; col--) {
				System.out.print(col);
			}
			for(int col=2; col<=row; col++) {
				System.out.print(col);
			}
			System.out.println();
		}
	}
}
