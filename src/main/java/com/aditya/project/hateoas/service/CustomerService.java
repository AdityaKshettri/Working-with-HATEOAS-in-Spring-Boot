package com.aditya.project.hateoas.service;

import com.aditya.project.hateoas.model.Customer;
import com.aditya.project.hateoas.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer findById(int id) {
        return customerRepository.findById(id).get();
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
