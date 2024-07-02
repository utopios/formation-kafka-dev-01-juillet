package com.example.fulfillmentservice.controller;

import com.example.fulfillmentservice.entity.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class OrderStatusController {

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderStatus(@PathVariable Long id) {
        //Aller chercher dans notre base de données la commande avec l'id
        return ResponseEntity.ok(Order.builder().id(id).build());
    }
}
