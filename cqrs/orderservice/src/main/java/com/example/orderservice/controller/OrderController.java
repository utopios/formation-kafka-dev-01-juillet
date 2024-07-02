package com.example.orderservice.controller;

import com.example.orderservice.entity.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final KafkaTemplate<Long, Order> kafkaTemplate;

    public OrderController(KafkaTemplate<Long, Order> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        order.setStatus("PENDING");
        //Avoir un répos pour gérer la partie des commandes
        //Idéalement il faut utiliser un service pour gérer la logique de kafka
        kafkaTemplate.send("orders", order.getId(), order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
