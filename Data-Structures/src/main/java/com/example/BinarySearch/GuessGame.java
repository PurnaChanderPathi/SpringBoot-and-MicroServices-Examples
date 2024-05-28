package com.example.BinarySearch;

public class GuessGame {
	public static void main(String[] args) {
		System.out.println(guessNumber(10));
	}	
    static int guessNumber(int n) {
        int start =1;
        int end = n;
        int mid = start + (end - start)/2;
        int p=guess(mid);
        while(guess(mid)!=0){
            if(guess(mid) ==1){
                start = mid + 1;
            }else if(guess(mid) ==-1){
                end = mid;
            }
            mid=start + (end - start)/2;
        }
        return mid;
}
    static int guess(int m) {
    	if(m > 6) {
    		return -1;
    	}else if(m < 6) {
    		return 1;
    	}else {
    		return 0;
    	}
    }
    
}
