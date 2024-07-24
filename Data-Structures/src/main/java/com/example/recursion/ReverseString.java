package com.example.recursion;

import java.util.Arrays;

public class ReverseString {
	public static void main(String[] args) {
		char[] input = {'h','e','l','l','o'};
		reverseString(input);
		System.out.println(Arrays.toString(input));
	}
	
	static void reverseString(char [] s) {
			reverseHelper(s,0,s.length-1);
	}

	private static void reverseHelper(char[] s, int left, int right) {
		if(left >= right) {
			return;
		}
		
		char temp = s[left];
		s[left] = s[right];
		s[right] = temp;
		
		reverseHelper(s, left + 1, right - 1);
		
	}
}
