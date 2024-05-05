package org.wikipedia.controller;

import okhttp3.Request;
import org.junit.jupiter.api.Test;
import org.wikipedia.model.PageSource;
import org.wikipedia.model.PageSourceWithHTML;

import java.io.IOException;
import java.util.Optional;

public class EndpointsHandleTest {

    private String baseUrl = "https://en.wikipedia.org/w/rest.php/v1/page";
    private String path = "/AI";
    private String pathWithHTML = "/with_html";

    @Test
    public void getGenericTest() throws IOException {
        EndpointsHandle endpointsHandle = new EndpointsHandle(baseUrl);
        Request request = endpointsHandle.createRequest("GET", path);
        Optional<PageSource> s = endpointsHandle.executeRequest(request, PageSource.class);
        System.out.println(s.get());
    }

    @Test
    public void getPageSourceTest() throws IOException {
        EndpointsHandle endpointsHandle = new EndpointsHandle(baseUrl);
        Optional<PageSource> pageSource = endpointsHandle.getPageSource(path);
        System.out.println(pageSource.get());

    }

    @Test
    public void getPageSourceWithHTMLTest() throws IOException {
        EndpointsHandle endpointsHandle = new EndpointsHandle(baseUrl);
        Optional<PageSourceWithHTML> pageSource = endpointsHandle.getPageSourceWithHTML(path + pathWithHTML);
        System.out.println(pageSource.get());

    }



}
