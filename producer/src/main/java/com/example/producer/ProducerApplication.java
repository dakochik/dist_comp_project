package com.example.producer;

import com.example.producer.configuration.KafkaProducerConfig;
import com.example.producer.service.DoubleArrayGeneratorService;
import com.example.producer.service.SimpleMessageGeneratorService;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Properties;

@EnableKafka
@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = SpringApplication.run(ProducerApplication.class, args);
       /* StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.enableCheckpointing(100);

        Properties kafkaProps = new Properties();
        kafkaProps.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        kafkaProps.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "click-event-count");

        environment.setParallelism(4);*/
        SimpleMessageGeneratorService service = applicationContext.getBean(SimpleMessageGeneratorService.class);
        service.startTask();
        DoubleArrayGeneratorService dservice = applicationContext.getBean(DoubleArrayGeneratorService.class);
        dservice.startTask();
    }
}
