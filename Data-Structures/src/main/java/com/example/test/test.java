package com.example.test;

public class test {
	
	public static void main(String[] args) {
		
		String[] a = {"Ram","Rama","Raja","Rani"};		
		String b = "";
		
		for(int i=0; i<a[0].length(); i++) {
			String ans = a[0].charAt(i)+"";
			int count =0;
			for(int j=0; j<a.length; j++) {
				if(ans.equals(a[j].charAt(i)+"")) {
					count++;
				}
				
			}if(count==a.length) {
				b = b+ans;
			}else {
				System.out.println(b);
				break;
			}
		}
	}

}
