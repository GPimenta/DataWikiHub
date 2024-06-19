package org.wikipedia.controller;

import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.wikipedia.util.PropertiesConfiguration;

import java.util.Properties;

//TODO Check if use testcontainers for Kakfa

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProducerTest {
    private final String bootStrapServer = "localhost:9092";
    private final String keySerializerClassName = StringSerializer.class.getName();
    private final String valueSerializerClassName = StringSerializer.class.getName();
    private Properties properties = PropertiesConfiguration.simpleProducerProperty(bootStrapServer, keySerializerClassName, valueSerializerClassName);


    @BeforeEach
    public void setUp() {

    }


    @Test
    public void producerCallBackTest() {
//        String bootStrapServer = "localhost:9092";
//        String keyDeserializerClassName = StringSerializer.class.getName();
//        String valueDeserializerClassName = StringSerializer.class.getName();
        Properties properties = PropertiesConfiguration.simpleProducerProperty(bootStrapServer, keySerializerClassName, valueSerializerClassName);
        String topicName = "topicTest";
        String key = "keyTest";
        String value = "valueTest";
        Producer producer = new Producer(properties, topicName);
        producer.producerCallBack(key, value);
    }
}
