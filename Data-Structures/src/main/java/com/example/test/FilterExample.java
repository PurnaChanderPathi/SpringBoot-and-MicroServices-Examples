package com.example.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilterExample {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Jane", "Jack", "Jill", "Bill");
        
//        // Filter names that start with "J"
//        List<String> filteredNames = names.stream()
//                                          .filter(name -> name.startsWith("J"))
//                                          .collect(Collectors.toList());
//
//        filteredNames.forEach(System.out::println);
        
        String [] str = {"John", "Jane", "Jack", "Jill", "Bill"};
        
//        List<String> filterN = names.stream().filter(name -> name.contains("B")).collect(Collectors.toList());
//        filterN.forEach(System.out::println);
        
        List<String> result = Arrays.stream(str).filter(n -> n.contains("J")).map(String::toLowerCase).collect(Collectors.toList());
        result.forEach(System.out::println);
    }
}
