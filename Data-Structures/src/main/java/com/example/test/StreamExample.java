package com.example.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamExample {
	public static void main(String[] args) {
//        // Creating a stream from a list
//        List<String> list = Arrays.asList("a", "b", "c");
//        Stream<String> stream = list.stream();
//        
//        // Creating a stream from an array
//        String[] array = {"x", "y", "z"};
//        Stream<String> streamFromArray = Arrays.stream(array);
//
//        // Creating a stream using Stream.of()
//        Stream<String> streamOf = Stream.of("1", "2", "3");
//
//        // Print all elements in the stream
//        stream.forEach(System.out::println);
//        streamFromArray.forEach(System.out::println);
//        streamOf.forEach(System.out::println);
        
        
        List<String> str = Arrays.asList("a","b","c");
        Stream<String> strem = str.stream();
        strem.forEach(System.out::println);
        
        String [] array = {"1","2","3"};
        Stream<String> str1 = Arrays.stream(array);
        str1.forEach(System.out::println);
        
        Stream<String> str2 = Stream.of("q","b","r");
        str2.forEach(System.out::println);
        
	}
}
