package com.example.service;

import java.util.List;

import com.example.model.OrderDetails;

public interface OrderService {
	
	public OrderDetails saveOrder(OrderDetails orderDetails);
	
	public List<OrderDetails> getAllOrder();

}
