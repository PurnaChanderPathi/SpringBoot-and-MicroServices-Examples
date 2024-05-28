package com.example.String;

public class Pattern17 {
	public static void main(String[] args) {
		pattern17(6);
	}
	
	static void pattern17(int n) {
		for(int row=0; row<2 * n; row++) {
			int C = row > n ? 2 * n -row : row;
			for(int s=0; s<n-C; s++) {
				System.out.print(" ");
			}
			for(int col=C; col>=1;col--) {
				System.out.print(col);
			}
			for(int col=2; col<=C; col++) {
				System.out.print(col);
			}
			System.out.println();
		}
	}
}
