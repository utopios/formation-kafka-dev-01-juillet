package com.example.trackingapp.service;

import com.example.trackingapp.entity.TrackingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TrackingEventProducer {
    private static final Logger logger = LoggerFactory.getLogger(TrackingEventProducer.class);
    private final KafkaTemplate<String, TrackingEvent> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    public TrackingEventProducer(KafkaTemplate<String, TrackingEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTrackingEvent(TrackingEvent event) {
        kafkaTemplate.send(topicName, event.getIdColis(), event);
        logger.info("Event sent: {}", event);
    }
}
