package com.example.test;

import java.util.Arrays;

public class MinimumNumberofDaystoMakemBouquets {
	public static void main(String[] args) {
		int[] input = {97,83}; 
		System.out.println(minDays(input, 2, 1));
	}
	
    public static int minDays(int[] bloomDay, int m, int k) {
        long length = bloomDay.length;
        long h = m*k;
        if(length<h) return -1;
        long count = 0;
        int[] sortbD = Arrays.copyOf(bloomDay, bloomDay.length);
        Arrays.sort(sortbD);
        long lengthlast = sortbD[bloomDay.length-1];
        long lengthfirst = sortbD[0];

        long j=1;
        if(length>=h){
        	if(k>1) {
        		int i=0;
        		boolean [] ch = new boolean[bloomDay.length];
            	for(long p=lengthfirst;p<=lengthlast;p++) {
            		i=0;
                	while(i<bloomDay.length){
                		if(p==bloomDay[i]) {
                			ch[i]=true;
                			i++;
                		}else {
                			i++;
                		}
                	}
                	int q=0;
                	long n=0;
                	count=0;
                	while(q<bloomDay.length) {
                		if(ch[q] ) {
                			count++;
                			q++;
                		}else {
                			count=0;
                			q++;
                		}
                		if(k==count) {
                			n++;
                			count=0;
                		}
                	}
                	if(n==m) {
                		return (int)p;
                	}
            	}       		
        	}else {
            	int i=0;
            	for(long p=lengthfirst;p<=lengthlast;p++) {
            		i=0;
                	while(i<bloomDay.length){
                		if(p==bloomDay[i]) {
                			count++;
                			i++;
                		}else {
                			i++;
                		}
                    	if(count==m) {
                    		return (int)p;
                    	}
                	}

            	}
        	}

        }
    return 0;
    }
}





/*
 * 	public static int method(int[] bloomDay, int m, int k)
	{
		
	        if(m*k>bloomDay.length) return -1; 
	        int max=0;
	        for(int i=0;i<bloomDay.length;i++) {
	        	max=Math.max(max, bloomDay[i]);
	        }
	        boolean [] ch = new boolean[bloomDay.length];
	        for(int i=0;i<bloomDay.length;i++) {
	        	if(ch[i]) continue;
	        	for(int j=i;j<bloomDay.length;j++) {
	        		if(bloomDay[i]>=bloomDay[j]) {
	        			ch[j]=true;
	        		}
	        	}
	        	int q=0;
            	int n=0;
            	int count=0;
            	while(q<bloomDay.length-1) {
            		if(ch[q] ) {
            			count++;
            			q++;
            		}else {
            			break;
            		}
            		if(k==count) {
            			n++;
            			count=0;
            		}
            	}
            	if(n==m) {
            		return bloomDay[i];
            	}
	        	
	        }
	        return -1;
	}
	*/
