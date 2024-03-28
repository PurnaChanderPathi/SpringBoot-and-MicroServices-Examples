package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.OrderDetails;

public interface OrderRepository extends JpaRepository<OrderDetails, Long> {

}
