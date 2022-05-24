package org.example;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        // 1.12.1
        Properties kafkaProps = new Properties();
        kafkaProps.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        kafkaProps.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "click-event-count");
        DataStream<String> kafkaDataStream = environment.addSource(new FlinkKafkaConsumer<>("simple_messages", new SimpleStringSchema(), kafkaProps))
                .name("DeliveryEvent Source");
        // 1.14.4
//        KafkaSource<String> source =
//                KafkaSource.<String>builder()
//                .setBootstrapServers("localhost:9092")
//                .setTopics("simple_messages")
//                .setGroupId("click-event-count")
//                .setStartingOffsets(OffsetsInitializer.earliest())
//                .setValueOnlyDeserializer(new SimpleStringSchema())
//                .build();
//        DataStream<String> kafkaDataStream = environment.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");

        kafkaDataStream.filter(it -> it.length() > 3).print();
        //kafkaDataStream.map(org.example.Main::mapper).print();

        System.out.println("EXECUTION...4");
        environment.execute();
    }

    public static String mapper(String input) throws Exception {
//        Thread.sleep(15000);
        return "TEXT: " + input;
    }
}
