package com.example.producer.serializer;

import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class DoubleArraySerializer implements Serializer<double[]> {

    @Override
    public byte[] serialize(String s, double[] doubles) {
        String tmp = "";
        String sep = ";";
        for (double d: doubles
             ) {
            tmp+=d+sep;
        }
        return new StringSerializer().serialize(s, tmp);
    }
}
