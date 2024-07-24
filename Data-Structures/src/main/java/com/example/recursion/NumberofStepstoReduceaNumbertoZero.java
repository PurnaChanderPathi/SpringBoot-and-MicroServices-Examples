package com.example.recursion;

public class NumberofStepstoReduceaNumbertoZero {
	public static void main(String[] args) {
		System.out.println(numberOfSteps(14));
	}

//	public static int numberOfSteps(int num) {
//		int count = 0;
//		while (num != 0) {
//			int result = num % 2;
//			if (num % 2 == 0) {
//				num = num / 2;
//				count++;
//			} else {
//				num = num - 1;
//				count++;
//			}
//		}
//		return count;
//	}
	
	public static int numberOfSteps(int num) {
		return numberHelper(num,0);
	}
	
	static int numberHelper(int num, int count) {
		if(num==0) return count;
		
			if(num % 2 == 0) {
				return numberHelper(num/2,count+1);
			}else {
				 return numberHelper(num-1,count+1);
			}
		
	}
	
}
