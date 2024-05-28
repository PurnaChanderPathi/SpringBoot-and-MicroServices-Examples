package com.example.String;

public class Pattern4 {
	public static void main(String[] args) {
		pattern4(5);
	}
	static void pattern4(int n) {
		for(int row=1; row<=n; row++) {
			int p=1;
			for(int col=1; col<=row; col++) {
				System.out.print(p+" ");
				p++; 
			}
			System.out.println();
		}
	}
}
