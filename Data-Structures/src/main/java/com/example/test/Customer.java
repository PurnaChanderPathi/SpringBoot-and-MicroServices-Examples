package com.example.test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Customer {

	private int customerid;
	private String name;
	private String city;
	
	
	
	public Customer(int customerid, String name, String city) {
		super();
		this.customerid = customerid;
		this.name = name;
		this.city = city;
	}



	public static void main(String[] args) {
		
		List<Customer> list = new ArrayList<>();
		Customer c1 = new Customer(1, "Pathi", "HYD");
		Customer c2 = new Customer(2, "Chandu", "Goa");
		Customer c3 = new Customer(1, "Purna", "VZ");
		list.add(c1);
		list.add(c2);
		list.add(c3);
		
//		List<Customer>  result = list.stream().filter((l)->l.name.contains("P")).collect(Collectors.toList());
//		for(var r:result) {
//			System.out.println(r.name);
//		}
		
		//List<Customer> result1 = list.stream().filter((item)->item.name.contains("P")).collect(Collectors.toList());
//		
		List<String> result2 = list.stream().map((e)->e.name).map(String::toLowerCase).collect(Collectors.toList());
		System.out.println(result2);
	}

}

