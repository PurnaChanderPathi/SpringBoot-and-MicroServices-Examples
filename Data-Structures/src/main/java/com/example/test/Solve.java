package com.example.test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solve {
	public static void main(String[] args) {
		List<java8> list = Arrays.asList(new java8(1, "Purna", 12000),
				new java8(2, "TAthi", 12000));
		
		List<java8> result = list.stream().filter((l)->l.ename.contains("T")).collect(Collectors.toList());
		for(var v:result) {
			System.out.println(v.ename);
		}
	}
}
