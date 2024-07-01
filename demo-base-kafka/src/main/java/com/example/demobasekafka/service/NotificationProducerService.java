package com.example.demobasekafka.service;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class NotificationProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public NotificationProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendCriticalNotification(String topic, String message) {
        try {
            kafkaTemplate.send(topic, message).get();
        }catch (InterruptedException | ExecutionException ex) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to send message", ex);
        }
    }

    public void sendGeneralNotification(String topic, String message) {
        kafkaTemplate.send(topic,message).whenCompleteAsync((result, ex) -> {
           if(ex != null) {
               System.out.println("Message sent successfully");
           }else {
               System.out.println("Failed to send message " +ex.getMessage());
           }
        });
    }
}
