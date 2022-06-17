package com.example.producer.utils;

import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

@Deprecated
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