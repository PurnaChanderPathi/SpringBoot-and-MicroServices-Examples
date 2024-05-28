package com.example.String;

public class Pattern9 {
public static void main(String[] args) {
	pattern9(9);
}
static void pattern9(int n) {
	int m=n;
	for(int row=0; row<n; row++) {
		for(int s=0; s<row; s++) {
			System.out.print(" ");
		}
		for(int col=0; col<m; col++){
			System.out.print("* ");
		}
		System.out.println();
		m--;
	}
}
}
