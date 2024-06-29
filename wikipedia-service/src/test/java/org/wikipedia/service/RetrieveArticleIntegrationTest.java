package org.wikipedia.service;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.MockConsumer;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.*;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.wikipedia.controller.EndpointsHandle;
import org.wikipedia.controller.Producer;
import org.wikipedia.util.DockerComposeManager;
import org.wikipedia.util.PropertiesConfiguration;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
public class RetrieveArticleIntegrationTest {
    private static final String KAFKA_SERVICE = "broker";
    private static final int KAFKA_PORT = 9092;
    private static final String MOCKSERVER_SERVICE = "mockserver";
    private static final int MOCKSERVER_PORT = 1080;

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

    private RetrieveArticle retrieveArticle;

    private EndpointsHandle endpointsHandle;

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
        String mockserverUrl = "http://" + dockerComposeManager.getServiceHost(MOCKSERVER_SERVICE, MOCKSERVER_PORT)
                + ":" + dockerComposeManager.getServicePort(MOCKSERVER_SERVICE, MOCKSERVER_PORT);

        Properties consumerProperty = PropertiesConfiguration.simpleConsumerProperty(kafkaBootstrapServers, keyDeserializerClassName, valueDeserializerClassName, groupId, offSetReset);
        Properties producerProperty = PropertiesConfiguration.simpleProducerProperty(kafkaBootstrapServers, keySerializerClassName, valueSerializerClassName);

        consumer = new KafkaConsumer<>(consumerProperty);
        consumer.subscribe(List.of(topic));

        producer = new Producer(producerProperty, topic);

        endpointsHandle = new EndpointsHandle(mockserverUrl);
        retrieveArticle = new RetrieveArticle(endpointsHandle, producer);
    }

    @AfterEach
    public void tearDown() {
        consumer.close();
    }

    @Test
    public void articlesToProducerTest() {

        String key = "test";
        String value = "{" +
                "\"id\":1," +
                "\"key\":\"test\"," +
                "\"title\":\"Test Title\"," +
                "\"latest\":{\"id\":1234}," +
                "\"content_model\":\"wikitext\"," +
                "\"license\":{\"url\":\"https://example.com\",\"title\":\"Example License\"}," +
                "\"source\":\"source content\"" +
                "}";

        MockServerClient mockServerClient = new MockServerClient(
                dockerComposeManager.getServiceHost(MOCKSERVER_SERVICE, MOCKSERVER_PORT),
                dockerComposeManager.getServicePort(MOCKSERVER_SERVICE, MOCKSERVER_PORT)
        );

//        mockServerClient.when(HttpRequest.request().withPath("/word"))
//                .respond(HttpResponse.response().withBody("{\"word\":[\"TestWord\"]}"));

        mockServerClient.when(HttpRequest.request().withPath("/word"))
                .respond(HttpResponse.response().withBody(value));

        retrieveArticle.articlesToProducer(1);

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

        for (ConsumerRecord<String, String> record : records) {
//            System.out.println("HERE " + record.key() + " " + record.value());
            assertEquals(key, record.key(), "Topic key are not being consumed correctly");
            assertEquals(value, record.value(), "Topic value are not being consumed correctly");
        }
    }

}
