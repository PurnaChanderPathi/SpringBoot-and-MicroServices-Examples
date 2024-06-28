package com.example.String;

import java.util.Arrays;

public class SortingTheSentence {
 public static void main(String[] args) {
	 String s="is2 sentence4 This1 a3";
	System.out.println(sortSentence(s));
}
 public static String sortSentence(String s) {
	 int k = 0;
	 String result="";
     String[] ss=s.split(" ");
     System.out.println(Arrays.toString(ss));
     String[] res=new String[s.length()];
     for(int i=0;i<ss.length;i++)
     {
      char[] ch=ss[i].toCharArray();
     for(int j=0;j<ch.length;j++)
     {
    	 if(j==ch.length-1)
    	 {
    		 k=(int) j;
    	 }
    	 else
    	 {
    		 char c=ch[j];
    		 result=result+c;
    	 }
    	 if(k==res.length)
    	 {
    		 res[k-1]=result;
    	 }
    	 else
    	 {
    		 res[k-1]=result+" ";
    	 }
    	
     }
      result=Arrays.toString(res);
     }
     return result;
 }
}
