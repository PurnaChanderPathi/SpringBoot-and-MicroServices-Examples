package com.example.BinarySearch;

import java.util.Arrays;


public class RowColMatrixSorted {
	public static void main(String[] args) {
		int[][] arr = {
				{1,2,3,4},
				{5,6,7,8},
				{9,10,11,12},
				{13,14,15,16}
		};
		System.out.println(Arrays.toString(search(arr, 15)));
	}
	
	// Search in the row provided between the columns provided
	static int[] binarySearch(int[][] matrix, int row, int cStart, int cEnd, int target) {
		while(cStart <= cEnd) {
			int mid = cStart + (cEnd - cStart)/2;
			if(matrix[row][mid]==target) {
				return new int[] {row,mid};
			}
			if(matrix[row][mid]<target) {
				cStart = mid + 1;
			}else {
				cEnd = mid -1;
			}
			
		}
		return new int[] {-1,-1};
	}
	
	static int[] search(int[][] arr, int target) {
		int row = arr.length;
		int col =arr[0].length; // be cautious, matrix may be empty
		
		if(row ==1) {
			return binarySearch(arr, 0, 0, col-1, target);
		}
		int rStart = 0;
		int rEnd = row-1;
		int cMid = col/2;
		
		//run the loop till 2 rows are remaining
		while(rStart < (rEnd - 1)) { //while this is true it will not more then 
			int mid = rStart + (rEnd - rStart)/2;
			if(arr[mid][cMid] == target) {
				return new int[] {mid,cMid};
			}
			if(arr[mid][cMid] < target) {
				rStart = mid;
			}else {
				rEnd = mid;
			}
		}
		// now we have two rows
		// check whether the target is in the col of 2 rows
		if(arr[rStart][cMid] == target) {
			return new int[] {rStart,cMid};
		}
		if(arr[rStart + 1][cMid] == target) {
			return new int[] {rStart+1,cMid};
		}
		
		//Search in 1st half
		if(target <=arr[rStart][cMid - 1]) {
			return binarySearch(arr, rStart, 0, cMid-1, target);
		}
		//Search in 2nd half
		if(target >=arr[rStart][cMid + 1] && target <= arr[rStart][col-1]) {
			return binarySearch(arr, rStart, cMid+1, col-1, target);
		}
		//Search in 3rd half
		if(target <=arr[rStart + 1][cMid - 1]) {
			return binarySearch(arr, rStart+1, 0, cMid-1, target);
		}else {
			return binarySearch(arr, rStart+1, cMid+1, col-1, target);
		}
		
	}
}
