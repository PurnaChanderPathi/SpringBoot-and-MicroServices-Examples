package com.example.test;

import java.util.ArrayList;
import java.util.List;

public class sample2 {
	public static void main(String[] args) {
		
		String str1 = "xyxyxy";
		String str2 = "xy";
		
		if(!(str1+str2).equals(str2+str1))
		{
			System.out.println("");
		}
		else
		{
		
		List<Character> list = new ArrayList();		
		StringBuilder builder = new StringBuilder();
		
		for(var w:str1.toCharArray()) {
			list.add(w);
		}
		for(var w2:str2.toCharArray()) {
			if(!list.isEmpty() && list.contains(w2)) {
				list.remove(Character.valueOf(w2));
			}
		}
		for(var sb:list) {
			builder.append(sb);
		}
		String s = builder.toString();
		System.out.println(s);
		}
	}
	
}
