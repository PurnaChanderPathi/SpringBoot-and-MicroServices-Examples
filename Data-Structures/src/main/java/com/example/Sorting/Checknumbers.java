package com.example.Sorting;

public class Checknumbers {
	public static void main(String[] args) {
		
		int[] a = {1,2,4,10,8}; 
		int target = 1;
		for(int i=0;i<a.length;i++)
		{
			for(int j=0;j<a.length;j++)
			{
				if(a[j]==target)
				{
					target++;
				}
			}
		}
		System.out.println(target);
		
			}
}
