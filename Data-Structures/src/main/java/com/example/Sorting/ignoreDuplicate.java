package com.example.Sorting;

public class ignoreDuplicate {
	public static void main(String[] args) {
		
		int visited = -1;
		int[] a= {1,2,3,3,4,2,1};
		int[] b=new int[a.length];
		for(int i=0; i<a.length; i++) {
 			int count = 0;
			for(int j=i+1; j<a.length; j++) {
				if(a[i]==a[j])
				{
					count++;
					b[j]=visited;
				}
			}
			 if(b[i] != visited)  
			 {
	                b[i] = count;  
	        }  
			
		}
		 for(int i = 0; i < b.length; i++){  
	            if(b[i] == 0)  
	                System.out.println( a[i] );  
	        }  
	}

}
