package org.stream_message.consumer;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stream_message.controller.ArticlesController;
import org.stream_message.model.PageSource;
import org.stream_message.model.PageSourcePostgres;
import org.stream_message.util.PageSourceMapper;

public class ArticleMessageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(ArticleMessageProcessor.class.getName());
    private final ArticlesController articlesController;
    private final Gson gson;

    public ArticleMessageProcessor(ArticlesController articlesController) {
        this.articlesController = articlesController;
        this.gson = new Gson();
    }

    public void processRecord(ConsumerRecord<String, String> record) {
        try {
            PageSource article = gson.fromJson(record.value(), PageSource.class);
            articlesController.setArticle(PageSourceMapper.toPostgres(article));
            logger.info("Processed record with key: " + record.key());
        } catch (Exception e) {
            logger.error("Failed to process record with key: " + record.key(), e);
        }
    }
}
