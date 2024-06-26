package org.wikipedia.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.*;
import org.wikipedia.util.DockerComposeManager;
import org.wikipedia.util.PropertiesConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
public class ProducerTest {
    private static final String KAFKA_SERVICE = "broker";
    private static final int KAFKA_PORT = 9092;
    private KafkaConsumer<String, String> consumer;
    private Producer producer;
    private DockerComposeManager dockerComposeManager;

    private final String keySerializerClassName = StringSerializer.class.getName();
    private final String valueSerializerClassName = StringSerializer.class.getName();
    private final String keyDeserializerClassName = StringDeserializer.class.getName();
    private final String valueDeserializerClassName = StringDeserializer.class.getName();
    private final String groupId = "test-group";
    private final String topic = "test-topic";
    private final String offSetReset = "earliest";

    @BeforeAll
    public void setUpClass() {
        dockerComposeManager = new DockerComposeManager();
        dockerComposeManager.startContainer();
    }

    @AfterAll
    public void tearDownClass() {
        dockerComposeManager.stopContainer();
    }

    @BeforeEach
    public void setUp() {
        String kafkaBootstrapServers = dockerComposeManager.getServiceHost(KAFKA_SERVICE, KAFKA_PORT) +
                ":" + dockerComposeManager.getServicePort(KAFKA_SERVICE, KAFKA_PORT);
        Properties consumerProperty = PropertiesConfiguration.simpleConsumerProperty(kafkaBootstrapServers, keyDeserializerClassName, valueDeserializerClassName, groupId, offSetReset);
        Properties producerProperty = PropertiesConfiguration.simpleProducerProperty(kafkaBootstrapServers, keySerializerClassName, valueSerializerClassName);

        consumer = new KafkaConsumer<>(consumerProperty);
        consumer.subscribe(List.of(topic));

        producer = new Producer(producerProperty, topic);
    }

    @AfterEach
    public void tearDown() {
        consumer.close();
    }

    @Test
    public void testProducer() {
        String key = "test-key";
        String value = "test-value";
        producer.producerCallBack(key, value);

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

        for (ConsumerRecord<String, String> record : records) {
            System.out.println("HERE " + record.key() + " " + record.value());
            assertEquals(key, record.key(), "Topic key are not being consumed correctly");
            assertEquals(value, record.value(), "Topic value are not being consumed correctly");
        }
    }

    @Test
    public void producerCallBackTest() {
//        String bootStrapServer = "localhost:9092";
//        String keyDeserializerClassName = StringSerializer.class.getName();
//        String valueDeserializerClassName = StringSerializer.class.getName();
//        Properties properties = PropertiesConfiguration.simpleProducerProperty(bootStrapServer, keySerializerClassName, valueSerializerClassName);
//        String topicName = "topicTest";
//        String key = "keyTest";
//        String value = "valueTest";
//        Producer producer = new Producer(properties, topicName);
//        producer.producerCallBack(key, value);
    }
}

