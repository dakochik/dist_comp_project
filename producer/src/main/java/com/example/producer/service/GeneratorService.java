package com.example.producer.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class GeneratorService {

    private Long delay = 200L;
    private Boolean started = false;
    private CompletableFuture<String> future;

    private final KafkaTemplate<Long, String> template;

    public GeneratorService(KafkaTemplate<Long, String> template) {
        this.template = template;
    }

    public synchronized void startTask() {
        if (!started) {
            started = true;
            future = CompletableFuture.supplyAsync(() -> {
                        while (started) {
                            try {
                                // TODO: generate message
                                String message = "Some message";
                                template.send("simple_messages", 1L, message);
                                Thread.sleep(delay);
                            } catch (InterruptedException ex) {
                                return "Thread was interrupted";
                            }
                        }
                        return "Done";
                    }
            );
        }
    }

    public synchronized void stopTask() {
        if (started) {
            started = false;
            future.cancel(true);
        }
    }

    public void changeDelay(Long delay) {
        if (delay <= 0) {
            throw new IllegalArgumentException("Delay must be positive");
        }

        synchronized (this.delay) {
            this.delay = delay;
        }
    }
}
