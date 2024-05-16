package com.example.Sorting;

public class Quotient {
    public static void main(String[] args) {
        int[] a = {500,200,100,50,20,10,5,2,1};
        int amount=14865;
        int quotient =0;
        for(int i=0; i<a.length; i++){
             quotient= amount/a[i];
             amount=amount%a[i];
            System.out.println(a[i]+"-"+quotient);
            }
        }

}
