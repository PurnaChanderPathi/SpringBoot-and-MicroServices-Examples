package com.example.test;

import java.util.Arrays;

public class Boat2 {

	public static void main(String[] args) {

		int[] weight = {3,5,3,4};
		int capacity = 5;
		int visited = -1;
		int ans = 0;
		int index = weight.length;
		Arrays.sort(weight);
      // System.out.println(Arrays.toString(weight));
		for (var i = 0; i < weight.length; i++) {
				for (var j = i + 1; j < weight.length; j++) {
					if(weight[i]==-1) {
						break;
					}
					if(weight[i]==5) {
						ans++;
						weight[i]=-1;
						break;
					}
					if(weight[i] + weight[j] <= capacity) {
						ans++;
						weight[i] = -1;
						weight[j] = -1;
					}else {
						ans++;
						weight[i] = -1;
						break;
					}
				}
					if(weight[weight.length-1]!=-1) {
						ans++;
						weight[weight.length-1]=-1;
					}
					
				}			
		System.out.println(ans);
	}
}
