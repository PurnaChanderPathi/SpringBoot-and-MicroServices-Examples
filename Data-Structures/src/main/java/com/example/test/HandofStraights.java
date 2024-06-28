package com.example.test;

public class HandofStraights {
	public static void main(String[] args) {
		 int[] input = {1,2,3,6,2,3,4,7,8}; 
				int groupSize = 3;
				System.out.println(isNStraightHand(input, groupSize));
	}
    public static boolean isNStraightHand(int[] hand, int groupSize) {
     	if(hand.length%groupSize==0) {
    		for(int i=0;i<hand.length-1;i++) {
    			for(int j=i+1; j>0; j--) {
    				if(hand[j]<hand[j-1]) {
    					int temp = hand[j-1];
    					hand[j-1] = hand[j];
    					hand[j] = temp;
    				}
    			}
    		}
    		int count = 0;
    		int p = 0;
    		int result =0;
    		while(p<hand.length) {
    			if(hand[p+1]==hand[p]+1) {
    				count++;
    				p++;
    			}
        		if(count==groupSize) {
        			
            		return true;
        		}
    		}

    }
        return false;
 }
}
    
