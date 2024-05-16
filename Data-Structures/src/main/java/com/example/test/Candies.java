package com.example.test;

import java.util.ArrayList;
import java.util.List;

public class Candies {
	public static void main(String[] args) {

		int[] candies = {12,1,12};
		int extraCandies=10;
		int max=candies[0];
		List<Boolean> list=new ArrayList<>();
		for(var i:candies)
		{
			if(i>max)
			{
				max=i;
			}
		}
		for( var i:candies)
		{
			if(i+extraCandies>=max)
			{
				list.add(true);
			}
			else
			{
				list.add(false);
			}
		}
		System.out.println(list);
		
		
		
	}	
}
