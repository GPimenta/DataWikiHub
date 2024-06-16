package org.wikipedia.controller;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


public class Producer {
    private final Properties properties;
    private final String topic;
//    private final String key;
//    private final String value;


    private static final Logger logger = LoggerFactory.getLogger(Producer.class.getSimpleName());

    public Producer(Properties properties, String topic) {
        this.properties = properties;
        this.topic = topic;
//        this.key = key;
//        this.value = value;
    }

    public void producerCallBack(String key, String value) {
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e == null) {
                    logger.info("\u001B[32m" + "Message sent to topic: {}, partition {}, offset {}" + "\u001B[0m", recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
                } else {
                    logger.error("Exception occurred when sending message to kafka producer");
                    throw new RuntimeException("Exception occurred when sending message to kafka producer");
                }

            }
        });
        producer.flush(); producer.close();
    }
}
