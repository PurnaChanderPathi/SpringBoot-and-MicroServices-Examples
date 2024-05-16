package com.example.arrays;

public class sample {
	public static void main(String[] args) {
		String a ="1234554321";
		int lengthofString = a.length();
		int endLength=lengthofString;
		int half = lengthofString/2;
		
		if(a.length()%2!=0) {
			System.out.println("False");
		}else {
			for(int i=0; i<half; i++) {
				char b = a.charAt(i);
				char c = a.charAt(endLength-1);
				if(b == c)
				{
					endLength--;
				}				
			}			
		}
		if(endLength==half)
		{
			System.out.println("true");
		}
		else
		{
			System.out.println("false");
		}
	}
}
