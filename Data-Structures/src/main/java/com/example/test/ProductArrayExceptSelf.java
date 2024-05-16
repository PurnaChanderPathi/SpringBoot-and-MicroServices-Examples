package com.example.test;

import java.util.ArrayList;
import java.util.List;

public class ProductArrayExceptSelf {
	public static void main(String[] args) {
		int[] num = {-1,1,0,-3,3};
		int result = 1;
		int[] answ = new int[num.length];
		List<Integer> ans = new ArrayList<>();
	for(int i=0; i<num.length; i++) {
		for(int j=0; j<num.length; j++) {
			if(i!=j) {
				if(result == 1 ) {
				 result =num[j];
				}else {
					result = result * num[j];
				}
			}
		}
		ans.add(result);
		result = 1;
	}
	for(int i=0; i<answ.length; i++) {
		answ[i]=ans.get(i);
	}
	System.out.println(ans);
	}

}
