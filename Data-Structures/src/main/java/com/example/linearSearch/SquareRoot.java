package com.example.linearSearch;

public class SquareRoot {
	public static void main(String[] args) {
		System.out.println(sqaureRoot(9));
	}
	
	static double sqaureRoot(int num) {
		double t;
		double sqrtroot = num/2;
		do {
			t=sqrtroot;
			sqrtroot =(t+(num/t))/2;			
		} while (t-sqrtroot != 0);
		int result = (int) sqrtroot;
		return result;
	}
}
