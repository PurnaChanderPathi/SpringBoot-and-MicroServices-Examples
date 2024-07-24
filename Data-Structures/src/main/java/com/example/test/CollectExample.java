package com.example.test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectExample {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Jane", "Jack", "Jill", "Bill", "Jane", "Jack");
        
//        // Collect to a list
//        List<String> namesList = names.stream()
//                                      .collect(Collectors.toList());
//
//        // Collect to a set
//        Set<String> namesSet = names.stream()
//                                    .collect(Collectors.toSet());
//
//        System.out.println("List: " + namesList);
//        System.out.println("Set: " + namesSet);
        
//        List<String> str = names.stream().collect(Collectors.toList());
//        str.forEach(System.out::println);
        
//        Set<String> str = names.stream().collect(Collectors.toSet());
//        str.forEach(System.out::println);
        
        Set<String> str = names.stream().collect(Collectors.toSet());
        str.forEach(System.out::println);
    }
}
