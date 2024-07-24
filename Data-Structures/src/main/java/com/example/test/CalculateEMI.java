package com.example.test;

import java.util.Arrays;
import java.util.List;

public class CalculateEMI {
	
	public static void main(String[] args) {
        List<Loan> loans = Arrays.asList(
            new Loan(100000, 10, 12),
            new Loan(200000, 8, 24),
            new Loan(300000, 12, 36)
        );

        loans.forEach(loan -> {
            double emi = calculateEMI(loan);
            System.out.println("EMI for loan with principal " + loan.getPrincipal() + " is: " + emi);
        });
    }


	public static double calculateEMI(Loan loan) {
        double principal = loan.getPrincipal();
        double annualInterestRate = loan.getAnnualInterestRate();
        int numberOfInstallments = loan.getNumberOfInstallments();

        // Convert annual interest rate to monthly and in decimal
        double monthlyInterestRate = (annualInterestRate / 100) / 12;

        // EMI calculation using the formula
        double emi = principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfInstallments) /
                     (Math.pow(1 + monthlyInterestRate, numberOfInstallments) - 1);

        return emi;
    }
}
