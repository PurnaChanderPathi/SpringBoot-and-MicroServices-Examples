package com.example.test;

public class FlowerPots {
	public static void main(String[] args) {
		
		int[] flowerPlots = {1,0,1,0,1};
		int n = 2;
		
		for(int i=0; i<flowerPlots.length; i++) {
			if(flowerPlots[i]==1) {

			}
			else if(flowerPlots[i]==0)
			{
					
					if(flowerPlots[i+1]==0) {
						if(n!=0) {
							flowerPlots[i+1]=1;
							n--;
						}
					}else if(flowerPlots[i+1]==1){						
				}
			}
		}
			if(n==0) {
				System.out.println("True");
			}else {
				System.out.println("False");
			}

	}
}
