package com.example.demobasekafka.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final KafkaTemplate<String, UserAvro> kafkaTemplate;

    public UserController(KafkaTemplate<String, UserAvro> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<?> sendUser(@RequestBody User user) {
        kafkaTemplate.send("user-json", user.getName(), UserAvro.builder().User(user).build());
        return ResponseEntity.ok().build();
    }
}
