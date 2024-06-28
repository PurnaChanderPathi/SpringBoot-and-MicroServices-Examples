package com.example.String;

import java.util.Arrays;

public class ReverseString {
	public static void main(String[] args) {
		char[] s = {'h','e','l','l','o'};
		System.out.println(Arrays.toString(reverseString(s)));
	}
	
	public static char[] reverseString(char[] s) {
	    char[] result = new char[s.length];
	    int n = s.length-1;
	    for(int i=0; i<s.length; i++){
	        result[n]=s[i];
	        n--;
	    }
	    return result;
	}
}


