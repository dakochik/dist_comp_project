package com.example.producer.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.concurrent.CompletableFuture;

@Service
public class GeneratorService {

    protected Long delay = 200L;
    protected Boolean started = false;
    protected CompletableFuture<String> future;

    protected KafkaTemplate template;

    protected GeneratorService(){}
    public GeneratorService(KafkaTemplate<Long, String> template) {
        this.template = template;
    }

    public synchronized void startTask() {
        if (!started) {
            started = true;
            future = CompletableFuture.supplyAsync(()->{return null;
            });
        }
    }

    protected synchronized void startTask(@NotNull java.util.function.Supplier supplier) {
        if (!started) {
            started = true;
            future = CompletableFuture.supplyAsync(supplier);
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
