package com.aditya.project.hateoas.controller;

import com.aditya.project.hateoas.model.Customer;
import com.aditya.project.hateoas.model.Order;
import com.aditya.project.hateoas.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public CollectionModel<Customer> findAll() {
        List<Customer> customers = customerService.findAll();
        for (Customer customer : customers) {
            Link link = linkTo(CustomerController.class).slash(customer.getId()).withSelfRel();
            customer.add(link);
            if (customer.getOrders().size() > 0) {
                customer.getOrders().forEach(order -> {
                    Link orderLink = linkTo(methodOn(OrderController.class).findById(order.getId())).withSelfRel();
                    order.add(orderLink);
                });
            }
        }
        Link link = linkTo(CustomerController.class).withSelfRel();
        return CollectionModel.of(customers, link);
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable int id) {
        Customer customer = customerService.findById(id);
        if (customer.getOrders().size() > 0) {
            customer.getOrders().forEach(order -> {
                Link orderLink = linkTo(methodOn(OrderController.class).findById(order.getId())).withSelfRel();
                order.add(orderLink);
            });
        }
        Link link = linkTo(CustomerController.class).slash(customer.getId()).withSelfRel();
        customer.add(link);
        return customer;
    }

    @GetMapping("/{id}/orders")
    public CollectionModel<Order> findOrdersByCustomerId(@PathVariable int id) {
        List<Order> orders = customerService.findOrdersByCustomerId(id);
        for (Order order : orders) {
            Link link = linkTo(methodOn(OrderController.class).findById(order.getId())).withSelfRel();
            order.add(link);
        }
        Link link = linkTo(methodOn(CustomerController.class).findOrdersByCustomerId(id)).withSelfRel();
        return CollectionModel.of(orders, link);
    }

    @GetMapping("/{id}/orders/{orderId}")
    public Order findOrderByCustomerIdAndOrderId(@PathVariable int id, @PathVariable int orderId) {
        Order order = customerService.findOrderByCustomerIdAndOrderId(id, orderId);
        Link link = linkTo(methodOn(OrderController.class).findById(orderId)).withSelfRel();
        order.add(link);
        return order;
    }
}
