package com.example.test;

public class MagneticForceBetweenTwoBalls {
	public static void main(String[] args) {
		int[] input = {5,4,3,2,1,1000000000};
		int m =2;
		System.out.println(maxDistance(input, 3));
	}
    public static int maxDistance(int[] position, int m) {
		int max=0;
    	for(int i=0; i<position.length;i++) {
            max = Math.max(max, position[i]);
    	}
    	boolean[] ch = new boolean[max+1];
    	for(int i=0;i<position.length;i++) {
    		ch[position[i]]=true;
    	}
    	

    	return 0;
    }
}
