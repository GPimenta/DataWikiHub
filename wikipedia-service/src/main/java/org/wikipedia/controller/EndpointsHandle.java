package org.wikipedia.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wikipedia.model.PageSource;
import org.wikipedia.model.PageSourceWithHTML;
import org.wikipedia.util.LocalDateTimeAdapter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;



public class EndpointsHandle {
    private static Logger logger = LoggerFactory.getLogger(EndpointsHandle.class);

    final private String baseUrl;
    final private Gson gson;
    private OkHttpClient client;


    public EndpointsHandle(String baseUrl) {
        this(baseUrl, new OkHttpClient.Builder()
                .connectTimeout(Duration.ofSeconds(15))
                .readTimeout(Duration.ofSeconds(15))
                .build());
    }

    public EndpointsHandle(String baseUrl, OkHttpClient client) {
        this.baseUrl = baseUrl;
        this.client = client;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

//    final private OkHttpClient client = new OkHttpClient.Builder()
//            .connectTimeout(Duration.ofSeconds(15))
//            .readTimeout(Duration.ofSeconds(15))
//            .build();

    public Request createRequest(String method, String path, String payload) {
        Request.Builder builder = new Request.Builder()
                .url(baseUrl + path);
        if (!(payload == null || payload.isEmpty())) {
            builder = builder
                    .method(method, RequestBody.create(payload.getBytes()))
                    .addHeader("Content-Type", "application/json");
        } else {
            builder = builder.method(method, null);
        }
        return builder
                .addHeader("Accept", "application/json")
                .addHeader("User-Agent", "MediaWiki REST API docs examples/0.1 (https://www.mediawiki.org/wiki/API_talk:REST_API)")
                .build();
    }

    public Request createRequest(String method, String path) {
        return createRequest(method, path, null);
    }

    public <T> Optional<T> executeRequest(Request request, Class<T> clazz) {
        try (Response response = client.newCall(request).execute()){
            if (response.code() >= 400) {
                logger.error("Unexpected status code{}", response.code());
                throw new RuntimeException("Unexpected status code" + response.code());
            }
            String payload = response.body().string();
            if (clazz == String.class) {
                return (Optional<T>) Optional.of(payload);
            }
            T value = gson.fromJson(payload, clazz);
            return Optional.of(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<PageSource> getPageSource(String path) {
        Request request = createRequest("GET", path);
        Optional<PageSource> pageSourceOptional = executeRequest(request, PageSource.class);
        if (pageSourceOptional.isPresent() && pageSourceOptional.get().getRedirectTarget() != null) {
            pageSourceOptional.get().setRedirectTarget("Redirection exists");
        }
        return pageSourceOptional;
    }

    public Optional<PageSourceWithHTML> getPageSourceWithHTML(String path) {
        Request request = createRequest("GET", path);
        Optional<PageSourceWithHTML> pageSourceWithHTMLOptional = executeRequest(request, PageSourceWithHTML.class);
        if (pageSourceWithHTMLOptional.isPresent() && pageSourceWithHTMLOptional.get().getRedirectTarget() != null) {
            pageSourceWithHTMLOptional.get().setRedirectTarget("Redirection exists");
        }
        return pageSourceWithHTMLOptional;
    }

//    public <T> Optional<T> getPage(String path, boolean isHTML) {
//        if (!isHTML) {
//            return (Optional<T>) getPageSource(path);
//        }
//        return (Optional<T>) getPageSourceWithHTML(path);
//    }
}
