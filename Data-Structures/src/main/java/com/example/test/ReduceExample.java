package com.example.test;

import java.util.Arrays;
import java.util.List;

public class ReduceExample {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // Sum all numbers
        int sum = numbers.stream()
                         .reduce(0, Integer::sum);

        System.out.println("Sum: " + sum);
    }
}
