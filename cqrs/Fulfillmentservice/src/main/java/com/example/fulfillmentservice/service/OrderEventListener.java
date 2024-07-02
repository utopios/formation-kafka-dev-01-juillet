package com.example.fulfillmentservice.service;


import com.example.fulfillmentservice.entity.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class OrderEventListener {

    @KafkaListener(topics = "orders")
    public void listenOrderEvents(Order order, Acknowledgment acknowledgment)  {
        order.setStatus("PROCESSED");
        acknowledgment.acknowledge();
    }
}
