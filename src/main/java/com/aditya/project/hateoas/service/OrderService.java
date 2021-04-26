package com.aditya.project.hateoas.service;

import com.aditya.project.hateoas.model.Order;
import com.aditya.project.hateoas.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order findById(int id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new NullPointerException("Order not found with id : " + id);
        }
        return order.get();
    }
}
