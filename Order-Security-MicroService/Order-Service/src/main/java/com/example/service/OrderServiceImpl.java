package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.OrderDetails;
import com.example.repo.OrderRepository;
@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public OrderDetails saveOrder(OrderDetails orderDetails) {
		return orderRepository.save(orderDetails);
	}

	@Override
	public List<OrderDetails> getAllOrder() {
		return orderRepository.findAll();
	}


}
