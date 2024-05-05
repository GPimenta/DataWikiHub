package org.wikipedia.controller;

import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.wikipedia.util.PropertiesConfiguration;

import java.util.Properties;

public class ProducerTest {

    @Test
    public void producerCallBackTest() {
        Properties properties = PropertiesConfiguration.simpleProducerProperty("localhost:9092", StringSerializer.class.getName(), StringSerializer.class.getName());
        String topicName = "topicTest";
        String key = "keyTest";
        String value = "valueTest";
        Producer producer = new Producer(properties, topicName);
        producer.producerCallBack(key, value);
    }
}
