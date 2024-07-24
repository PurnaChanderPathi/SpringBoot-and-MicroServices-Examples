package com.example.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapExample {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Jane", "Jack", "Jill", "Bill");
        
//        // Convert all names to uppercase
//        List<String> upperCaseNames = names.stream()
//                                           .map(String::toUpperCase)
//                                           .collect(Collectors.toList());
//
//        upperCaseNames.forEach(System.out::println); 
        
//        List<String> result = names.stream().map(String::toLowerCase).collect(Collectors.toList());
//        result.forEach(System.out::println);
        
      List<String> result = names.stream().map(String::toLowerCase).collect(Collectors.toList());
      result.forEach(System.out::println);
        
        
    }
}
