package com.example.trackingapp.controller;

import com.example.trackingapp.entity.TrackingEvent;
import com.example.trackingapp.service.TrackingEventProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tracking")
public class TrackingEventController {
    private final TrackingEventProducer producer;

    public TrackingEventController(TrackingEventProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<Void> sendTrackingEvent(@RequestBody TrackingEvent event) {
        producer.sendTrackingEvent(event);
        return ResponseEntity.ok().build();
    }
}
