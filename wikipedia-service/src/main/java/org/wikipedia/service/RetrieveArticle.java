package org.wikipedia.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wikipedia.controller.EndpointsHandle;
import org.wikipedia.controller.Producer;
import org.wikipedia.model.PageSource;
import org.wikipedia.model.PageSourceWithHTML;
import org.wikipedia.model.RandomWord;
import org.wikipedia.util.LocalDateTimeAdapter;
import org.wikipedia.util.RandomWordDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RetrieveArticle {
    private static Logger logger = LoggerFactory.getLogger(RetrieveArticle.class);

    private final EndpointsHandle endpointsHandle;
    private final Producer producer;
    final private Gson gson;

    public RetrieveArticle(EndpointsHandle endpointsHandle, Producer producer) {
        this.endpointsHandle = endpointsHandle;
        this.producer = producer;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(RandomWord.class, new RandomWordDeserializer())
                .create();
    }

    public PageSource getPageSource(String path) throws IOException {
        return endpointsHandle.getPageSource(path).orElseThrow(() -> new RuntimeException("Article page not working in Service"));
    }

    public PageSourceWithHTML getPageSourceWithHTML(String path) throws IOException {
        return endpointsHandle.getPageSourceWithHTML(path).orElseThrow(() -> new RuntimeException("Article with HTML page not working in Service"));
    }

//    public RandomWord getRandomWord(String path) throws IOException {
//        return endpointsHandle.getRandomWordPage(path).orElseThrow(() -> new RuntimeException("Random Word page not working in Service"));
//    }

    public void producerArticle(String key, String value) {
        producer.producerCallBack(key, value);
    }

    public PageSource getPageSourceWithRandomWord(String word) {
        try {
            return getPageSource("/" + word);
        } catch (Exception e) {
            throw new RuntimeException("Error in getting the wikipedia article with random word");
        }
    }

    public void articlesToProducer(int numberOfArticles) {
        while (0 < numberOfArticles) {
            try {
                PageSource pageSource = getPageSourceWithRandomWord("/word");
                producerArticle(pageSource.getKey(), gson.toJson(pageSource));
                numberOfArticles--;
            } catch (Exception e) {
                logger.error("Error in sending article through Kafka", e);
                throw new RuntimeException("Error in sending article through Kafka", e);
            }
        }
    }





//    public void articlesToProducer(List<String> articleList) throws IOException {
//        articleList.forEach(
//                article -> {
//                    try {
//                        Optional<PageSource> pageSource = getPageSource("/" + article);
//                        pageSource.ifPresentOrElse(source -> producerArticle(source.getKey(), gson.toJson(source)),
//                                () -> {throw new RuntimeException("Page source is empty");});
//                    } catch (IOException e) {
//                        throw new RuntimeException("Error in sending article through kafka" + e.getMessage());
//                    }
//                });
//    }
//
//    public void articlesWithHTMLToProducer(List<String> articleList) throws IOException {
//        articleList.forEach(
//                article -> {
//                    try {
//                        Optional<PageSourceWithHTML> pageSourceWithHTML = getPageSourceWithHTML("/" + article);
//                        pageSourceWithHTML.ifPresentOrElse(source -> producerArticle(source.getKey(), gson.toJson(source)),
//                                () -> {throw new RuntimeException("Page source is empty");});
//                    } catch (IOException e) {
//                        throw new RuntimeException("Error in sending article through kafka" + e.getMessage());
//                    }
//                });
//    }
}
