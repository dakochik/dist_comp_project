package com.example.producer.service;

import com.example.producer.utils.Generator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class SimpleMessageGeneratorService extends GeneratorService {

    public SimpleMessageGeneratorService(/*@Qualifier("kafkaStringTemplate")*/ KafkaTemplate<Long, String> template) {
        this.template = template;
    }

    @Override public synchronized void startTask() {
        Supplier supplier = () -> {
            while (started) {
                try {
                    //String message = "Some message";
                    String message = Generator.getRString();
                    template.send("simple_messages", 1L, message);
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    return "Thread was interrupted";
                }
            }
            return "Done";
        };
        startTask(supplier);
    }


}
