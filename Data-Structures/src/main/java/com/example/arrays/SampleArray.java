package com.example.arrays;

public class SampleArray {
	public static void main(String[] args) {
		String a ="{([{([])}])}";
		String f = "{}";
		String p = "()";
		String d = "[]";
		int x =0;
		int y =1;
		int lengthofString = a.length();
		int endLength=lengthofString;
		int half = lengthofString/2;
		
		if(a.length()%2!=0) {
			System.out.println("False");
		}else {
			for(int i=0; i<half; i++) {
				char b = a.charAt(i);
				char c = a.charAt(endLength-1);
				if(b == f.charAt(x))
				{
					if(c == f.charAt(y)) {
						endLength--;
					}
					else {
						break;
					}
				}
				else if(b == p.charAt(x))
				{
					if(c == p.charAt(y)) {
						endLength--;
					}
					else {
						break;
					}
				}
				else if(b == d.charAt(x))
				{
					if(c == d.charAt(y)) {
						endLength--;
					}
					else {
						break;
					}
				}else {
					System.out.println("false");
					break;
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
}
