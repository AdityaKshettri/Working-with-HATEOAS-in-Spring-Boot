package com.aditya.project.hateoas.controller;

import com.aditya.project.hateoas.model.Order;
import com.aditya.project.hateoas.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public Order findById(@PathVariable int id) {
        Order order = orderService.findById(id);
        Link link = linkTo(OrderController.class).slash(id).withSelfRel();
        order.add(link);
        return order;
    }
}
