package org.wikipedia.controller;

import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.wikipedia.util.PropertiesConfiguration;

import java.util.Properties;

public class ProducerTest {

    @Test
    public void producerCallBackTest() {
        String bootStrapServer = "localhost:9092";
        String keyDeserializerClassName = StringSerializer.class.getName();
        String valueDeserializerClassName = StringSerializer.class.getName();
        Properties properties = PropertiesConfiguration.simpleProducerProperty(bootStrapServer, keyDeserializerClassName, valueDeserializerClassName);
        String topicName = "topicTest";
        String key = "keyTest";
        String value = "valueTest";
        Producer producer = new Producer(properties, topicName);
        producer.producerCallBack(key, value);
    }
}
