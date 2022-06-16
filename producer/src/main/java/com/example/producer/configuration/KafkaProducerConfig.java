package com.example.producer.configuration;

import com.example.producer.utils.DoubleArraySerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public Map<String, Object> producerStringConfigs() {
        Map<String, Object> props = new HashMap<>();
        String kafkaServer = "kafka:9092";
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        return props;
    }

    //check it
    /*
    @Bean
    public Map<String, Object> producerDoubleArrayConfigs() {
        Map<String, Object> props = new HashMap<>();
        String kafkaServer = "kafka:9092";
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                DoubleArraySerializer.class);
        return props;
    }*/

    @Bean
    public ProducerFactory<Long, String> producerStringFactory() {
        return new DefaultKafkaProducerFactory<>(producerStringConfigs());
    }

    @Bean
    public KafkaTemplate<Long, String> kafkaStringTemplate() {
        return new KafkaTemplate<>(producerStringFactory());
    }

  /*  @Bean
    public ProducerFactory<Long, double[]> producerDoubleArrayFactory() {
        return new DefaultKafkaProducerFactory<>(producerDoubleArrayConfigs());
    }

    @Bean
    public KafkaTemplate<Long, double[]> kafkaDoubleArrayTemplate() {
        return new KafkaTemplate<>(producerDoubleArrayFactory());
    }*/

}
