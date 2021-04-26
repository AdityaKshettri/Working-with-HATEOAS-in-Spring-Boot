package com.aditya.project.hateoas.controller;

import com.aditya.project.hateoas.model.Customer;
import com.aditya.project.hateoas.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Customer> findAll() {
        List<Customer> customers = customerService.findAll();
        for(Customer customer: customers) {
            Link link = linkTo(CustomerController.class).slash(customer.getId()).withSelfRel();
            customer.add(link);
        }
        return customers;
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable int id) {
        Customer customer = customerService.findById(id);
        Link link = linkTo(CustomerController.class).slash(customer.getId()).withSelfRel();
        customer.add(link);
        return customer;
    }
}
