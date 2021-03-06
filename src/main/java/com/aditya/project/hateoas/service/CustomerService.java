package com.aditya.project.hateoas.service;

import com.aditya.project.hateoas.model.Customer;
import com.aditya.project.hateoas.model.Order;
import com.aditya.project.hateoas.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer findById(int id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new NullPointerException("Customer not found with id : " + id);
        }
        return customer.get();
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Order findOrderByCustomerIdAndOrderId(int customerId, int orderId) {
        Customer customer = findById(customerId);
        for (Order order : customer.getOrders()) {
            if (order.getId() == orderId) {
                return order;
            }
        }
        throw new NullPointerException("Order not found by id : " + orderId + " for the customer id : " + customerId);
    }

    public List<Order> findOrdersByCustomerId(int id) {
        Customer customer = findById(id);
        return customer.getOrders();
    }
}
