package org.wikipedia.service;

import com.google.gson.Gson;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.wikipedia.controller.EndpointsHandle;
import org.wikipedia.controller.Producer;
import org.wikipedia.model.PageSource;
import org.wikipedia.model.PageSourceWithHTML;
import org.wikipedia.util.PropertiesConfiguration;
import org.wikipedia.util.ReadFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class RetrieveArticleTest {
    private String baseUrl = "https://en.wikipedia.org/w/rest.php/v1/page";
    private String path = "/banana";
    private String pathWithHTML = "/with_html";
    private String bootstrap = "localhost:9092";
    private String topic = "topicTest2";
    List<String> articleList = ReadFile.getList("src/main/java/org/wikipedia/articles/ArticlesList.txt");
    final private Gson gson = new Gson();


    EndpointsHandle endpointsHandle = new EndpointsHandle(baseUrl);
    Producer producer = new Producer(PropertiesConfiguration.simpleProducerProperty(bootstrap, StringSerializer.class.getName(), StringSerializer.class.getName()), topic);
    RetrieveArticle retrieveArticle = new RetrieveArticle(endpointsHandle, producer);

    public RetrieveArticleTest() throws IOException {
    }


    @Test
    public void getPageSourceTest() throws IOException {
        Optional<PageSource> pageSource = endpointsHandle.getPageSource(path);
        assert(pageSource.isPresent());
    }

    @Test
    public void getPageSourceWithHTMLTest() throws IOException {
        Optional<PageSourceWithHTML> pageSource = endpointsHandle.getPageSourceWithHTML(path + pathWithHTML);
        assert(pageSource.isPresent());
    }

    @Test
    public void producerArticleTest() throws IOException {
        Optional<PageSource> pageSource = endpointsHandle.getPageSource(path);
        retrieveArticle.producerArticle(pageSource.get().getKey(), gson.toJson(pageSource.get()));
    }

    @Test
    public void articlesToProducerTest() throws IOException {
        retrieveArticle.articlesToProducer(articleList);
    }

    @Test
    public void articlesWithHTMLToProducer() throws IOException {
        retrieveArticle.articlesWithHTMLToProducer(articleList);
    }
}
