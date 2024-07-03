package com.example.trackingappconsumer.service;

import com.example.trackingappconsumer.entity.TrackingEvent;
import com.example.trackingappconsumer.repository.TrackingEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class TrackingEventConsumer {
    private static final Logger logger = LoggerFactory.getLogger(TrackingEventConsumer.class);
    private final TrackingEventRepository repository;

    public TrackingEventConsumer(TrackingEventRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(TrackingEvent event, Acknowledgment  acknowledgment) {
        repository.save(event);
        logger.info("Event consumed: {}", event);
        acknowledgment.acknowledge();
    }
}
