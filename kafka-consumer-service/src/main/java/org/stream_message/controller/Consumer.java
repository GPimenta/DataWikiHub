package org.stream_message.controller;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Consumer {
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class.getName());
    private Gson gson = new Gson();

    private final Properties properties;
    private final List<String> topics;

    public Consumer(Properties properties, List<String> topics) {
        this.properties = properties;
        this.topics = topics;
    }

    public <T> void consumerSimpleTopic(Class<T> clazz) {
        Map<String, T> map = new HashMap<>();
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        consumer.subscribe(topics);

        try {
            while (true) {
                logger.info("Polling messages");
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println(record.key() + " " + record.value());
                    map.put(record.key(), gson.fromJson(record.value(), clazz));
                }
            }
        } catch (Exception e) {
            logger.error("Unexpected error {0}", e);
        }
    }

//    public void consumerSimpleTesting(){
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
//
//        consumer.subscribe(topics);
//
//        while (true) {
//            logger.info("polling");
//            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
//
//            for (ConsumerRecord <String, String> record : records) {
//                System.out.println(record.key() + " " + record.value());
//
//            }
//        }
//    }
}
