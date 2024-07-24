package com.example.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PipelineExample {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Jane", "Jack", "Jill", "Bill");
        
        // Pipeline: filter names that start with "J", convert to uppercase, and collect to list
        List<String> result = names.stream()
                                   .filter(name -> name.startsWith("J"))
                                   .map(String::toUpperCase)
                                   .collect(Collectors.toList());

        result.forEach(System.out::println);
    }
}
