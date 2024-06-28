package com.example.test;

public class HeightChecker {
public static void main(String[] args) {
	int[] input = {1,2,3,4,5};
	System.out.println(heightChecker(input));
}
public static int heightChecker(int[] heights) {
    int result = 0;
    int[] resultList = new int[heights.length];
    System.arraycopy(heights, 0, resultList, 0, heights.length);
    for(int i=0; i<heights.length-1; i++){
        for(int j=i+1; j>0;j--){
            if(heights[j-1]>heights[j]){
                int temp = heights[j-1];
                heights[j-1] = heights[j];
                heights[j] = temp;
            }
        }
    }
    for(int p=0; p<heights.length;p++) {
    	int s=0;
    	while( s<resultList.length) {
    		if(heights[p]!=resultList[s]) {
    			result++;
    			p++;
    			s++;
    		}else {
    			p++;
    			s++;
    		}
    	}
    }
    
    return result;
}
}
