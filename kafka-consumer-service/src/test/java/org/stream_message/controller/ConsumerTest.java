package org.stream_message.controller;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.stream_message.connector.PostgresSQLJDBC;
import org.stream_message.consumer.ArticleMessageProcessor;
import org.stream_message.consumer.Consumer;
import org.stream_message.model.PageSource;
import org.stream_message.repository.ArticlesDAO;
import org.stream_message.util.PropertiesConfiguration;

import java.util.List;
import java.util.Properties;

public class ConsumerTest {

    private static String bootStrapServer = "localhost:9092";
    private static String keyDeserializerClassName = StringDeserializer.class.getName();
    private static String valueDeserializerClassName = StringDeserializer.class.getName();
    private static String groupId = "Banana7";
    private static String offSetReset = "earliest";


    private final String host = "localhost";
    private final String port = "5432";
    private final String database = "WIKI_ARTICLES";
    private final PostgresSQLJDBC postgresSQLJDBC = new PostgresSQLJDBC(host, port, database);
    private final ArticlesDAO articlesDAO = new ArticlesDAO(postgresSQLJDBC);
    private final ArticlesController articlesController = new ArticlesController(articlesDAO);
    private final ArticleMessageProcessor messageProcessor = new ArticleMessageProcessor(articlesController);

    @Test
    public void consumerSimpleTopicTest() {

        String topic = "topicTest";
        Properties properties = PropertiesConfiguration.simpleConsumerProperty(bootStrapServer, keyDeserializerClassName, valueDeserializerClassName, groupId, offSetReset);
        List<String> topicsList = List.of(topic);

        Consumer consumer = new Consumer(properties, topicsList);
        consumer.consumerSimpleTopic(String.class);
    }

    @Test
    public void consumerSimpleTopicWithArticleTest() {
        String topic = "topicTest3";
        Properties properties = PropertiesConfiguration.simpleConsumerProperty(bootStrapServer, keyDeserializerClassName, valueDeserializerClassName, groupId, offSetReset);
        List<String> topicList = List.of(topic);

        Consumer consumer = new Consumer(properties, topicList, messageProcessor);
        consumer.consumerSimpleTopic(PageSource.class);

    }


//    @Test
//    public void consumerSimpleTest() {
//        Properties properties = new Properties();
//        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
//        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "Banana4");
//        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//        String topic = "topicTest";
//        List<String> topicsList = List.of(topic);
//
//        Consumer consumer = new Consumer(properties, topicsList);
//        consumer.consumerSimpleTesting();
//    }
}
