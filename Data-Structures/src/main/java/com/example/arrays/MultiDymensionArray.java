package com.example.arrays;

import java.util.Arrays;
import java.util.Scanner;

public class MultiDymensionArray {
	public static void main(String[] args) {
		/*
		 * 1 2 3
		 * 4 5 6
		 * 7 8 9
		 */
		
		//  int[] [] arr = new int[3][3];
		
//		int[] [] arr = {
//				{1,2,3}, //0th index
//				{4,5},	//1st index
//				{6,7,8,9} //2nd index -> arr[2] = {6,7,8,9};
//		};
		
		Scanner scr = new Scanner(System.in);
		int[][] arr = new int[3][3];
		System.out.println(arr.length);
		
//		for(int i=0; i< arr.length; i++) {
//			arr[i] = scr.nextInt();
//		}
		
		for(int row = 0; row<arr.length; row++ ) {
			// for each col in every row
			for(int col=0; col<arr[row].length; col++) {
				arr[row][col] = scr.nextInt();
			}
		}
		
		// output 
		for(int row = 0; row<arr.length; row++ ) {
			// for each col in every row
			System.out.println(Arrays.toString(arr[row]));
		}
	}
}
