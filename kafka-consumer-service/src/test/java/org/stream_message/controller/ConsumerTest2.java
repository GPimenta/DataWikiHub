package org.stream_message.controller;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stream_message.consumer.Consumer;
import org.stream_message.util.DockerComposeManager;
import org.stream_message.util.PropertiesConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Properties;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
public class ConsumerTest2 {
    private static final Logger logger = LoggerFactory.getLogger(ConsumerTest2.class.getSimpleName());

    private static final String KAFKA_SERVICE = "broker";
    private static final int KAFKA_PORT = 9092;
    private KafkaProducer<String, String> producer;
    private Consumer consumer;
    private DockerComposeManager dockerComposeManager;

    private final String keySerializerClassName = StringSerializer.class.getName();
    private final String valueSerializerClassName = StringSerializer.class.getName();
    private final String keyDeserializerClassName = StringDeserializer.class.getName();
    private final String valueDeserializerClassName = StringDeserializer.class.getName();
    private final String groupId = "test-group";
    private final String topic = "test-topic";
    private final String offSetReset = "earliest";

    private final String key = "Value";
    private final String value = "{\n" +
            "\"id\": 47645,\n" +
            "\"key\": \"Value\",\n" +
            "\"title\": \"Value\",\n" +
            "\"latest\": {\n" +
            "\"id\": 1215787694,\n" +
            "\"timestamp\": \"2024-03-27T03:22:08Z\"\n" +
            "},\n" +
            "\"content_model\": \"wikitext\",\n" +
            "\"license\": {\n" +
            "\"url\": \"https://creativecommons.org/licenses/by-sa/4.0/deed.en\",\n" +
            "\"title\": \"Creative Commons Attribution-Share Alike 4.0\"\n" +
            "},\n" +
            "\"source\": \"{{wiktionary|valuable|value|value types|valued|values}}\\n{{wikiquote}}\\n'''Value''' or '''values''' may refer to:\\n{{TOCright}}\\n== Ethics and social sciences ==\\n* [[Value (ethics and social sciences)]] wherein said concept may be construed as treating actions themselves as abstract objects, associating value to them\\n** [[Values (Western philosophy)]] expands the notion of value beyond that of ethics, but limited to Western sources \\n* [[Social imaginary]] is the '''set of values''', institutions, laws, and symbols common to a particular [[social group]]\\n* [[Religious values]] reflect the beliefs and practices which a religious adherent partakes in\\n\\n== Economics ==\\n* [[Value (economics)]], a measure of the benefit that may be gained from goods or service\\n** [[Theory of value (economics)]], the study of the concept of economic value\\n** [[Value (marketing)]], the difference between a customer's evaluation of benefits and costs\\n** [[Value investing]], an investment paradigm\\n* [[Values (heritage)]], the measure by which the cultural significance of heritage items is assessed\\n* [[Present value]]\\n* [[Present value of benefits]]\\n\\n== Business ==\\n* [[Business value]]\\n* [[Customer value proposition]]\\n* [[Employee value proposition]]\\n* [[Value (marketing)]]\\n* [[Value proposition]]\\n\\n== Other uses ==\\n* Value, also known as [[lightness]] or tone, a representation of variation in the perception of a color or color space's brightness\\n* [[Value (computer science)]], an expression that implies no further mathematical processing; a \\\"normal form\\\"\\n* [[Value (mathematics)]], a property such as number assigned to or calculated for a variable, constant or expression\\n* [[Value (semiotics)]], the significance, purpose and/or meaning of a symbol as determined or affected by other symbols\\n* [[Note value]], the relative duration of a musical note\\n* [[Values (political party)]], a defunct New Zealand environmentalist political party\\n\\n== See also ==\\n{{Canned search|value}}\\n* [[Instrumental and intrinsic value]]\\n* [[Value theory]], a range of approaches to understanding how, why, and to what degree people value things\\n* {{srt}}\\n* [[Valu (disambiguation)]]\\n\\n{{Disambiguation}}\"\n" +
            "}";

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

        producer = new KafkaProducer<>(producerProperty);
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
    }

    @AfterEach
    public void tearDown() {
        producer.flush(); producer.close();
    }

    @Test
    public void testConsumer() {

    }

}
