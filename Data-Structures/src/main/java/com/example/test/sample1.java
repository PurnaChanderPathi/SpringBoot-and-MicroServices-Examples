package com.example.test;


public class sample1 {
public static void main(String[] args) {

	String a = "abc";
	String b = "wxyz";	
	String ans = "";
	int n=a.length();
	int m=b.length();
	
	for(var i=0; i<n || i<m; i++) {
		if(i<n)
		{
		char c = a.charAt(i);
		ans = ans + c;
		}
		if(i<m)
		{
		char d = b.charAt(i);
		ans = ans + d;
		}
	}
System.out.println(ans);
	}
}
