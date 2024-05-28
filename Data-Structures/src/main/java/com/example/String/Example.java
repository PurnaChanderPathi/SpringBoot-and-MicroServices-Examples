package com.example.String;

public class Example {
	public static void main(String[] args) {
		String a = "Purna";
		String b = "Purna";
		// refer same object for String a & b
		//==
		System.out.println(a==b); // TRUE
		
		String c = new String("Purna");
		String d = new String("Purna");
		System.out.println(c==d);
		// gives false it have two separate objects with same values
		
		// when you want to check only value use .equals method
		System.out.println(c.equals(d));
	}
}
