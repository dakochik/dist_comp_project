package com.example.producer.service;

import com.example.producer.utils.Generator;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.function.Supplier;

@Service
public class DoubleArrayGeneratorService extends GeneratorService{
    public DoubleArrayGeneratorService(/*@Qualifier("kafkaStringTemplate")*/ KafkaTemplate<Long, String> template){
        this.template = template;
    }

    @Override
    public synchronized void startTask() {
        Supplier supplier = () -> {
            while (started) {
                try {
                    String array = Arrays.stream(Generator.getRArr())
                            .mapToObj((double d) -> d +";")
                            .reduce((String s1, String s2) -> s1+s2)
                            .get();
                    template.send("double_array_in_str", 1L, array);
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
