package com.example.test;

public class ReverseWords {

	public static void main(String[] args) {
		String s = "a good   example";
		int slength = s.length();
		String ss = s.trim().replaceAll("\\s+"," ");
		int sslength = ss.length();
		String ans = "";
		String[] words = s.split(" ");
		
		int k=words.length>0?words.length-1:-1;
		for(int i=0; i<words.length; i++) {
			if(k>0) {
				String trim = words[k].trim();
				int trimlength = trim.length();
				ans+=trim+" ";
				k--;
			}else if(k==0) {
				String trim = words[k].trim();
				int trimlength = trim.length();
				ans+=trim;
				k--;
			}
		}
		System.out.println(ans);
	}
	
}
