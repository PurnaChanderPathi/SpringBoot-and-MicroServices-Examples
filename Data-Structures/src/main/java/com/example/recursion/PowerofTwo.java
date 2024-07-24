package com.example.recursion;

public class PowerofTwo {
	
	public static void main(String[] args) {
		
		System.out.println(isPower(2147483647));
		
	}
	
//    static boolean isPower(int n){
//    	int p=0;
//        for(int x=0; x<n; x++){
//            int result = (int) Math.pow(2,p);
//
//            if(result == n){
//                return true;
//            }
//            p++;
//        }
//		return false;
//    }
    
    static boolean isPower(int n) {
    	return isPowerHelper( n, 0, 2);
    }

    static boolean isPowerHelper(int n, int p, int pow) {
    	long result = (long) Math.pow(pow, p);
    	if(result == n) {
    		return true;
    	}else if(result > n) {
    		return false;
    	}else {
    		return isPowerHelper(n, p+1, pow);
    	}
}

}
