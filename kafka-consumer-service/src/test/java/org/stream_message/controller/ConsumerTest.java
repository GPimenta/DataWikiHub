package org.stream_message.controller;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.stream_message.model.PageSource;
import org.stream_message.util.PropertiesConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ConsumerTest {

    @Test
    public void consumerSimpleTopicTest() {

        String bootStrapServer = "localhost:9092";
        String keyDeserializerClassName = StringDeserializer.class.getName();
        String valueDeserializerClassName = StringDeserializer.class.getName();
        String groupId = "SecondTest";
        String offSetReset = "earliest";
        String topic = "topicTest";
        Properties properties = PropertiesConfiguration.simpleConsumerProperty(bootStrapServer, keyDeserializerClassName, valueDeserializerClassName, groupId, offSetReset);
        List<String> topicsList = List.of(topic);
        Consumer consumer = new Consumer(properties, topicsList);

        Map<String, String> stringPageSourceMap = consumer.consumerSimpleTopic(String.class);

        stringPageSourceMap.forEach((key, value) -> System.out.println("key -> " + key + " value -> " + value));

    }
}
