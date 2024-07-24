package com.example.test;

import java.util.Arrays;
import java.util.List;

public class EmiCalculation {
	
//	public static void main(String[] args) {
//		
//		List<emi> emis = Arrays.asList(
//				new emi(1000, 10, 12),
//				new emi(10000, 20, 10)
//				);
//		
//		emis.forEach(emi -> {
//			double emiCal = calculateLoan(emi);
//			System.out.println("your emi principal : "+emi.getPrincipal()+" Emi per monthly :"+emiCal);
//		});
//	}
//	
//	private static double calculateLoan(emi emi) {
//		double principal = emi.getPrincipal();
//		double annualIntrestEmi = emi.getAnnualInterestRate();
//		int numberOfInstallments = emi.getNumberOfInstallments();
//		
//		//convert annualRate of Interest to monthRate
//		double monthlyIntrestRate = (annualIntrestEmi / 100)/12;
//		
//		
//		double emiResult =  principal * monthlyIntrestRate * Math.pow(1 + monthlyIntrestRate, numberOfInstallments)/
//				(Math.pow(1 + monthlyIntrestRate, numberOfInstallments)-1);
//		return emiResult;
//	}
	
	public static void main(String[] args) {
		
		List<emi> emis = Arrays.asList(
				new emi(10000, 5, 12),
				new emi(50000, 10, 24)
				);
		
		emis.forEach(emi -> {
			double emiCalculation = calculateEmi(emi);
			System.out.println("Your Monthly Emi : "+emiCalculation);
		});
	}

	private static double calculateEmi(emi emi) {
	double principal = emi.getPrincipal();
	double annualInterestRate = emi.getAnnualInterestRate();
	int numberOfInstallments = emi.getNumberOfInstallments();
	
	// convert annual to monthly intrestRate
	double monthlyIntRate = (annualInterestRate/100)/12;
	
	double emiResult = (principal * monthlyIntRate) * Math.pow(1+ monthlyIntRate, numberOfInstallments)/
			(Math.pow(1+ monthlyIntRate, numberOfInstallments)-1);
	return emiResult;
}

	static class emi{
		private double principal;
	    private double annualInterestRate;
	    private int numberOfInstallments;
		public double getPrincipal() {
			return principal;
		}
		public void setPrincipal(double principal) {
			this.principal = principal;
		}
		public double getAnnualInterestRate() {
			return annualInterestRate;
		}
		public void setAnnualInterestRate(double annualInterestRate) {
			this.annualInterestRate = annualInterestRate;
		}
		public int getNumberOfInstallments() {
			return numberOfInstallments;
		}
		public void setNumberOfInstallments(int numberOfInstallments) {
			this.numberOfInstallments = numberOfInstallments;
		}
		public emi(double principal, double annualInterestRate, int numberOfInstallments) {
			super();
			this.principal = principal;
			this.annualInterestRate = annualInterestRate;
			this.numberOfInstallments = numberOfInstallments;
		}	    	    
	}
}


