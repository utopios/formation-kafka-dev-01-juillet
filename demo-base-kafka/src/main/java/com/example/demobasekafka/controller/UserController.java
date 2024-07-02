package com.example.demobasekafka.controller;

import com.example.demobasekafka.dto.User;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {
    private final KafkaTemplate<String, User> kafkaTemplate;

    public UserController(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<?> sendUser(@RequestBody User user) {
        kafkaTemplate.send("user", user.getName(), user);
        return ResponseEntity.ok().build();
    }
}
