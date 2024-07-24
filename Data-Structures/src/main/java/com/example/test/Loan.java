package com.example.test;

public class Loan {

	private double principal;
    private double annualInterestRate;
    private int numberOfInstallments;

    public Loan(double principal, double annualInterestRate, int numberOfInstallments) {
        this.principal = principal;
        this.annualInterestRate = annualInterestRate;
        this.numberOfInstallments = numberOfInstallments;
    }

    public double getPrincipal() {
        return principal;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }
}
