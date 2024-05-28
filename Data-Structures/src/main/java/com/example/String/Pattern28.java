package com.example.String;

public class Pattern28 {
public static void main(String[] args) {
	pattern6(5);
}
static void pattern6(int n) {
	for(int row=0; row<2 * n; row++) {
		int totalColsInRow = row > n ? 2 * n - row: row;
		int noOfSpaces = n - totalColsInRow;
		for(int spc=0; spc<noOfSpaces; spc++) {
			System.out.print(" ");
		}
		for(int col=0; col<totalColsInRow; col++) {
			System.out.print("* ");
		}
		System.out.println();
	}
}
}
