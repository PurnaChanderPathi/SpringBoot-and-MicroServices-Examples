package com.example.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortExample {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Jane", "Jack", "Jill", "Bill");
        
//        // Sort names alphabetically
//        List<String> sortedNames = names.stream()
//                                        .sorted()
//                                        .collect(Collectors.toList());
//
//        sortedNames.forEach(System.out::println);
        
//        List<String> str = names.stream().sorted().collect(Collectors.toList());
//        str.forEach(System.out::println);
        
        List<String> str = names.stream().sorted().collect(Collectors.toList());
        for(var st : str) {
        	System.out.println(st);
        }
    }
}
