package com.example.producer;

import com.example.producer.service.DoubleArrayGeneratorService;
import com.example.producer.service.SimpleMessageGeneratorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.EnableKafka;

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
