package org.wikipedia.service;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wikipedia.controller.EndpointsHandle;
import org.wikipedia.controller.Producer;
import org.wikipedia.model.PageSource;
import org.wikipedia.model.PageSourceWithHTML;
import org.wikipedia.util.ReadFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class RetrieveArticle {
    private static Logger logger = LoggerFactory.getLogger(RetrieveArticle.class);

    private final EndpointsHandle endpointsHandle;
    private final Producer producer;
    final private Gson gson = new Gson();

    public RetrieveArticle(EndpointsHandle endpointsHandle, Producer producer) {
        this.endpointsHandle = endpointsHandle;
        this.producer = producer;
    }

    public Optional<PageSource> getPageSource(String path) throws IOException {
        return endpointsHandle.getPageSource(path);
    }

    public Optional<PageSourceWithHTML> getPageSourceWithHTML(String path) throws IOException {
        return endpointsHandle.getPageSourceWithHTML(path);
    }

    public void producerArticle(String key, String value) {
        producer.producerCallBack(key, value);
    }

    public void articlesToProducer(List<String> articleList) throws IOException {
        articleList.forEach(
                article -> {
                    try {
                        Optional<PageSource> pageSource = getPageSource("/" + article);
                        pageSource.ifPresentOrElse(source -> producerArticle(source.getKey(), gson.toJson(source)),
                                () -> {throw new RuntimeException("Page source is empty");});
                    } catch (IOException e) {
                        throw new RuntimeException("Error in sending article through kafka" + e.getMessage());
                    }
                });
    }

    public void articlesWithHTMLToProducer(List<String> articleList) throws IOException {
        articleList.forEach(
                article -> {
                    try {
                        Optional<PageSourceWithHTML> pageSourceWithHTML = getPageSourceWithHTML("/" + article);
                        pageSourceWithHTML.ifPresentOrElse(source -> producerArticle(source.getKey(), gson.toJson(source)),
                                () -> {throw new RuntimeException("Page source is empty");});
                    } catch (IOException e) {
                        throw new RuntimeException("Error in sending article through kafka" + e.getMessage());
                    }
                });
    }
}
