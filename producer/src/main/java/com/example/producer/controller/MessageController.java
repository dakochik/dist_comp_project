package com.example.producer.controller;

import com.example.producer.service.GeneratorService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msg")
public class MessageController {

    private final KafkaTemplate<Long, String> template;
    private final GeneratorService generatorService;

    public MessageController(KafkaTemplate<Long, String> template, GeneratorService generatorService) {
        this.template = template;
        this.generatorService = generatorService;
    }

    @PostMapping("/send")
    public void sendMessage(@RequestParam Long messageId, @RequestParam String message) {
        template.send("simple_messages", messageId, message);
    }

    @PostMapping("/start")
    public void startGenerating() {
        generatorService.startTask();
    }

    @PostMapping("/stop")
    public void endGenerating() {
        generatorService.stopTask();
    }

    @PostMapping("/setDelay")
    public void changeDelay(@RequestParam Long delay) {
        generatorService.changeDelay(delay);
    }
}
