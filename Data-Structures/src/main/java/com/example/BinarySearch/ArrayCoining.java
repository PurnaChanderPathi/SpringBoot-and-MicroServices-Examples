package com.example.BinarySearch;

public class ArrayCoining {
	 public static void main(String[] args) {
	       int n=3;
	       int a=1;
	       int count=0;
	       while(n>0)
	       {
	           if(a<=n)
	           {
	               count++;
	           }
	           n=n-a;
	           a++;
	       }
	       System.out.println(count);
	    }
}
