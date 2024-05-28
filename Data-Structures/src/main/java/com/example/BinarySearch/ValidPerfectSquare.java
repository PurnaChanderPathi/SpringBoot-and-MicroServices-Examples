package com.example.BinarySearch;

public class ValidPerfectSquare {
	public static void main(String[] args) {
		System.out.println(isPerfectSquare(1));
	}

	static boolean isPerfectSquare(int num) {

		if (num < 2)
			return true;
		for (int i = 2; i < num; i++) {
			int square = i * i;
			if (square == num) {
				return true;
			} else if (square > num) {
				break;
			}
		}
		return false;
	}
}
