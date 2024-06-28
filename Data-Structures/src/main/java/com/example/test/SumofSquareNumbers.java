package com.example.test;

public class SumofSquareNumbers {
	public static void main(String[] args) {
		int c = 100000;
		System.out.println(judgeSquareSum(c));
		
	}
    public static boolean judgeSquareSum(int c) {
    	long i=0;
    	long j=(long)Math.sqrt(c);
    	while(i<=j) {
    		long result = i*i+j*j;
    		if(result<c) {
    			i++;
    		}else if(result>c) {
    			j--;
    		}else {
    			return true;
    		}
    	}
        return false;
    }
}
