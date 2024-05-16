package com.example.linearSearch;

public class MaxWealth {
	public static void main(String[] args) {
		int[][] accounts = {
				{1,2,3},
				{3,2,1}
		};
		System.out.println(maximumWealth(accounts));
		System.out.println(maximumWealths(accounts));
	}
	
	// Method 1
	static int maximumWealth(int[][] accounts) {
		//person row
		//account col
		int ans = Integer.MIN_VALUE;
		for(int person=0; person<accounts.length; person++) {
			int sum = 0;
			//when you started num col, take a num sum for that sum
			for(int account=0; account<accounts[person].length; account++) {
				sum = sum + accounts[person][account];
			}
			if(sum > ans) {
				ans = sum;
			}
		}
		return ans;
	}
	
	// Method 2
	static int maximumWealths(int[][] accounts) {
		//person row
		//account col
		int ans = Integer.MIN_VALUE;
		for(int[] ints : accounts) {
			int sum = 0;
			//when you started num col, take a num sum for that sum
			for(int anInt : ints) {
				sum = sum + anInt;
			}
			if(sum > ans) {
				ans = sum;
			}
		}
		return ans;
	}
}
