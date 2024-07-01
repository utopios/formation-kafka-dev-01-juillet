package com.example.demobasekafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class DataConsumer {

//    @KafkaListener(topics = "demo_topic", groupId = "demo-id")
//    public void listenData(String message, Acknowledgment acknowledgment) {
//        System.out.println("Received message "+message);
//        acknowledgment.acknowledge();
//
//    }
    @KafkaListener(topics = "demo_topic", groupId = "demo-id")
    public void listenData(List<ConsumerRecord<String,String>> recordList, Acknowledgment acknowledgment) {
        for (ConsumerRecord<String, String> record : recordList) {
            System.out.println("Received message: " + record.value() + " from partition: " + record.partition());
        }
        acknowledgment.acknowledge();

    }
}
