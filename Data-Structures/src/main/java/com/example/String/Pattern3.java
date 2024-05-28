package com.example.String;

public class Pattern3 {
	public static void main(String[] args) {
		pattern1(4);
	}
	static void pattern1(int n) {
		int m=n;
		for(int row=1; row<=n; row++) {
			for(int col=1; col<=m; col++) {
				System.out.print("* ");
			}
			m--;
			System.out.println();
		}
	}
}
