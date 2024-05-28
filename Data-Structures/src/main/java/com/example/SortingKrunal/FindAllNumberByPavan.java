package com.example.SortingKrunal;

import java.util.ArrayList;
import java.util.List;

public class FindAllNumberByPavan {
 public static void main(String[] args) {
	int[] a= {4,3,2,7,8,2,3,1};
	System.out.println(ggg(a));
	
}
 static List<Integer> ggg(int[] a)
	{
	 List<Integer> list=new ArrayList<>();
	 boolean[] b=new boolean[a.length+1];
	 for(var i:a)
	 {
		 b[i]=true;
	 }
	 
	 for(int i=0;i<b.length;i++)
	 {
		 if(!b[i])
		 {
			 list.add(i);
		 }
	 }
	 list.remove(0);
	return list;	
	}
}
