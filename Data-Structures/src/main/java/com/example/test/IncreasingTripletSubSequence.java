package com.example.test;

public class IncreasingTripletSubSequence {
	public static void main(String[] args) {
		int[] nums = {5,4,3,2,1};

		int count = 0;
		int x = nums.length;
		for (int i = 0; i < nums.length; i++) {
			count = 1;
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[i] < nums[j]) {

					count=2;
					for (int k = j + 1; k < nums.length; k++) {
						if (nums[j] < nums[k]) {
							count=3;
							System.out.println("True");
							return;
						}
					}
				}
			}
		}
		System.out.println("False");
	}
}
