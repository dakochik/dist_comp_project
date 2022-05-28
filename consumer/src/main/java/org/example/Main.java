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
        environment.enableCheckpointing(1000);

//         1.12.1
        Properties kafkaProps = new Properties();
        kafkaProps.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
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

        System.out.println("EXECUTION...");

        DataStream<String> eventProcessed = kafkaDataStream.map(it -> "MESSAGE SIZE: " + it.length());
//
//        final FileSink<String> sink = FileSink
//                .forRowFormat(new Path("/tmp/flink-output"), new SimpleStringEncoder<String>("UTF-8"))
//                .withRollingPolicy(
//                        DefaultRollingPolicy.builder()
//                                .withRolloverInterval(TimeUnit.SECONDS.toMillis(15))
//                                .withInactivityInterval(TimeUnit.SECONDS.toMillis(5))
//                                .withMaxPartSize(128 * 128 * 128)
//                                .build())
//                .build();
//
//        eventProcessed.sinkTo(sink);

        eventProcessed.print();

        environment.execute();
    }
}
