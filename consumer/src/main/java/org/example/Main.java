package org.example;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.map.CustomBogoMap;
import org.example.map.CustomBubbleMap;
import org.example.map.CustomMap;

import java.util.Properties;

public class Main {


    private static void event (DataStream<String> kafkaDataStream, RichMapFunction<String,String> map){
        System.out.println("EXECUTION...");
        DataStream<String> eventProcessed = kafkaDataStream.map(map);
     /* final FileSink<String> sink = FileSink
                .forRowFormat(new Path("/tmp/flink-output"), new SimpleStringEncoder<String>("UTF-8"))
                .withRollingPolicy(
                        DefaultRollingPolicy.builder()
                                .withRolloverInterval(TimeUnit.SECONDS.toMillis(15))
                                .withInactivityInterval(TimeUnit.SECONDS.toMillis(5))
                                .withMaxPartSize(128 * 128 * 128)
                                .build())
                .build();
        eventProcessed.sinkTo(sink);*/
        eventProcessed.print();
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.enableCheckpointing(100);//1_000

//         1.12.1
        Properties kafkaProps = new Properties();
        kafkaProps.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        kafkaProps.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "click-event-count");
        kafkaProps.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                LongDeserializer.class.getName());
        kafkaProps.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());

       DataStream<String> kafkaDataStream = environment.addSource(new FlinkKafkaConsumer<>("simple_messages", new SimpleStringSchema(), kafkaProps))
                .name("DeliveryEvent Source simple messages");
        DataStream<String> kafkaDataStream2 = environment.addSource(new FlinkKafkaConsumer<>("double_array", new SimpleStringSchema(), kafkaProps))
                .name("DeliveryEvent Source double arrays");
      event(kafkaDataStream, new CustomMap());

       // event(kafkaDataStream, new CustomReverseMap());
        //event(kafkaDataStream, new CustomBogoMap());
        event(kafkaDataStream2, new CustomBubbleMap());
        event(kafkaDataStream2, new CustomBogoMap());

        environment.execute();
    }
}
 /* kafkaDataStream.addSink(
                new KafkaSink<>(
                        "simple_messages",
                        new SimpleStringSchema(),
                        kafkaProps)).name("Producer simple messages").setParallelism(2);
*/
//   new SimpleMessageGeneratorService(new KafkaProducerConfig().kafkaStringTemplate()).startTask();
// 1.14.4
       /* KafkaSource<String> source =
                   KafkaSource.<String>builder()
                .setBootstrapServers("localhost:9092")
                .setTopics("simple_messages")
                .setGroupId("click-event-count")
                .setStartingOffsets(OffsetsInitializer.earliest())
               // .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();*/
// DataStream<String> kafkaDataStream3 = environment.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");
//  new DoubleArrayGeneratorService(new KafkaProducerConfig().kafkaStringTemplate()).startTask();
