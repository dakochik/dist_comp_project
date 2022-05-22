package com.example.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msg")
public class MessageController {

    @Autowired
    private KafkaTemplate<Long, String> template;

    @PostMapping
    public void sendMessage(@RequestParam Long messageId, @RequestParam String message) {
        template.send("simple_messages", messageId, message);
    }
}
