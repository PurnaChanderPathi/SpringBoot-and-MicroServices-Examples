package com.example.String;

public class Pattern5 {
public static void main(String[] args) {
	//pattern5(5);
	pattern5Bykunal(5);
}

// my method
//static void pattern5(int n) {
//	for(int row=1; row<=n; row++) {
//		for(int col=1; col<=row; col++) {
//
//			System.out.print("* ");
//		}
//		System.out.println();
//	}
//	int m=n-1;
//	for(int row=1; row<=n; row++) {
//		for(int col=1; col<=m; col++) {
//
//			System.out.print("* ");
//		}
//		m--;
//		System.out.println();
//
//	}
//	
//}

static void pattern5Bykunal(int n) {
	for(int row=0; row<2 * n; row++) {
		int totalColsInRow = row > n ? 2 * n - row: row;
		for(int col=0; col<totalColsInRow; col++) {
			System.out.print("* ");
		}
		System.out.println();
	}
}
}
