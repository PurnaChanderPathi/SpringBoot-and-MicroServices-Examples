//package com.example.String;
//
//import java.util.ArrayList;
//import java.util.List;
//public class FindCommonCharacters {
//	public static void main(String[] args) {
//		String[] input = {"bella","label","roller"};
//		System.out.println(commonChars(input));
//	}
//	
//    public static List<String> commonChars(String[] words) {
//        List<String> result = new ArrayList<>();
//        StringBuilder str = new StringBuilder();
//        for(int i=0;i<words.length-1;i++)
//        {
//            String s1 =words[i];
//            String s2 =words[i+1];
//            if(s1!=words[0]){
//            	for(int s=0; s<words.length; s++) {
//            		for(String ch : result) {
//            			str.append(ch);
//            		}
//            		s1=result;
//            	}
//            }
//            
//            char[] c1 = s1.toCharArray();
//            char[] c2 = s2.toCharArray();
//            
//            for(int j=0; j<c1.length; j++) {
//            	for(int k=0; k<c2.length; k++) {
//            		if(c1[j]==c2[k])
//            		{
//            			result.add(Character.toString(c1[j]));
//            			c2[k] = '1';
//            			break;
//            		}
//            	}            	            	
//            } 
//
//
//        }
//
//        return result2;
//    }
//}
