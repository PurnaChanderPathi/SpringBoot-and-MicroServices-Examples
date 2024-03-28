package com.example.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.OrderDetails;
import com.example.service.OrderService; 

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/save")
	public OrderDetails save(@RequestBody OrderDetails orderDetails) {
		return orderService.saveOrder(orderDetails);
	}
	
	@GetMapping("/getAll")
	public List<OrderDetails> getAll(){
		return orderService.getAllOrder();
	}

}
