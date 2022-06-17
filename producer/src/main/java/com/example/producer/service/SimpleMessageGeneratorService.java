package com.example.producer.service;

import ch.qos.logback.classic.pattern.ClassNameOnlyAbbreviator;
import com.example.producer.utils.Generator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

@Service
public class SimpleMessageGeneratorService extends GeneratorService {

    public SimpleMessageGeneratorService(/*@Qualifier("kafkaStringTemplate")*/ KafkaTemplate<Long, String> template) {
        this.template = template;
    }

    @Override public synchronized void startTask() {
        Supplier supplier = () -> {
            changeDelay(1L);
            while (started) {
                try {
                    //String message = "Some message";
                    String message = Generator.getRString();
                    template.send("simple_messages", 1L, message);
                    System.out.println(message);
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
