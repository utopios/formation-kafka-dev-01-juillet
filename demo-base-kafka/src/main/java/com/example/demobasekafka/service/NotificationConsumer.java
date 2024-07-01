package com.example.demobasekafka.service;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationConsumer {
    @KafkaListener(topics = "notifications", groupId = "general-group")
    public void listenGeneral(List<ConsumerRecord<String,String>> recordList, Acknowledgment acknowledgment) {
        for (ConsumerRecord<String, String> record : recordList) {
            System.out.println("Received message: " + record.value() + " from partition: " + record.partition());
        }
        acknowledgment.acknowledge();
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "notifications", partitions = {"0"}), groupId = "critical-group")
    public void listenCritical(List<ConsumerRecord<String,String>> recordList, Acknowledgment acknowledgment) {
        for (ConsumerRecord<String, String> record : recordList) {
            System.out.println("Received message: " + record.value() + " from partition: " + record.partition());
        }
        acknowledgment.acknowledge();
    }
}
