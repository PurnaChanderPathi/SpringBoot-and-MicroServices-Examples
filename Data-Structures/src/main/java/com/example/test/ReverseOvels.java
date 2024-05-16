package com.example.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReverseOvels {
		public static void main(String[] args) {
			String input = "leetcode";
			List<String> vowels =Arrays.asList("a","e","i","o","u"); 			
			String ans = "";
			List<String> addTo = new ArrayList<>();
			
			for(int i=0; i<input.length(); i++) { 
				String currentChar = String.valueOf(Character.toLowerCase(input.charAt(i)));
				if(vowels.contains(currentChar)) {
					addTo.add(String.valueOf(input.charAt(i)));
				}
			}
			int k=addTo.size()>0?addTo.size()-1:-1;
			for(int i=0; i<input.length(); i++) {
				String currentChar = String.valueOf(Character.toLowerCase(input.charAt(i)));
				if(vowels.contains(currentChar)) {
					if(k>-1)ans+=addTo.get(k--);
				}else {
					ans+=input.charAt(i)+"";
				}

			}
			System.out.println(ans);

		}

}
