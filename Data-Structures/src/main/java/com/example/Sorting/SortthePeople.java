package com.example.Sorting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SortthePeople {
	
	public static void main(String[] args) {
		String[] names = {"IEO","Sgizfdfrims","QTASHKQ","Vk","RPJOFYZUBFSIYp","EPCFFt","VOYGWWNCf","WSpmqvb"};
		int[] heights = {17233,32521,14087,42738,46669,65662,43204,8224};
		System.out.println(Arrays.toString(sortPeople(names, heights)));
	}
	
    public static String[] sortPeople(String[] names, int[] heights) {
//        for(int x=0; x<heights.length;x++){
//        	for(int j=0; j<heights.length-1; j++) {
//                if(heights[j]<heights[j+1]) {
//                	int tempo = heights[j+1];
//                	heights[j+1] = heights[j];
//                	heights[j] = tempo;
//                	String temp = names[j+1];
//                	names[j+1] = names[j];
//                	names[j] = temp;
//                }
//        	}
//        }
//        return names;
    	Map<Integer, String> map = new HashMap<>();
    	for(int x=0; x<heights.length;x++) {
    		map.put(heights[x], names[x]);
    	}
    	Arrays.sort(heights);
    	int index = 0;
    	for(int j=heights.length-1;j>=0;j--) {
    		if(map.containsKey(heights[j])){
    			names[index++]=map.get(heights[j]);
    		}
    	}
    	return names;
    } 
}
