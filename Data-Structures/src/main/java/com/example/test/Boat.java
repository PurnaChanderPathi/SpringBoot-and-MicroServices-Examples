package com.example.test;

import java.lang.reflect.Array;

public class Boat {
	public static void main(String[] args) {

		int[] weight = {5, 1, 3, 3, 4, 2, 6,1,4,5,1,2};
		int capacity = 6;
		int visited = -1;
		int ans = 0;

		for (var i = 0; i < weight.length; i++) {
				for (var j = i + 1; j < weight.length; j++) {
					if(weight[i]==-1) {
						break;
					}
					if(weight[i]==6) {
						ans++;
						weight[i]=-1;
					}
					if(weight[i] + weight[j] <= capacity) {
						ans++;
						weight[i] = -1;
						weight[j] = -1;
					}
				}			
		}
		System.out.println(ans);
	}
}
