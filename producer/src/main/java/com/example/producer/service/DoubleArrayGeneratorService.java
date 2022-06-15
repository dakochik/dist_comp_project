package com.example.producer.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class DoubleArrayGeneratorService extends GeneratorService {

    public DoubleArrayGeneratorService(@Qualifier("kafkaDoubleArrayTemplate") KafkaTemplate<Long, double[]> template){
        this.template = template;
    }

    @Override public synchronized void startTask() {
        Supplier supplier = () -> {
            while (started) {
                try {
                    // TODO: generate message
                    double[] array = getRArr();
                    template.send("double_array", 1L, array);
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    return "Thread was interrupted";
                }
            }
            return "Done";
        };
        startTask(supplier);
    }

    //Get random number in range [min;max)
    private double getRNInRange(int min, int max){
        return Math.random()*max - min;
    }

    //Get random array with size len and numbers in range [min;max)
    private double[] getRArr(int len, int min, int max){
        double[] result = new double[len];
        for (int i = 0; i<len;i++){
            result[i] = getRNInRange(min, max);
        }
        return result;
    }

    //Get random array with size len and numbers in range [MinInt; MaxInt)
    private double[] getRArr(int len){
        return getRArr(len, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    //Get random array with size in range [1;100_000) and numbers in range [MinInt; MaxInt)
    private double[] getRArr(){
        return getRArr((int)getRNInRange(1,100_000));
    }
}
