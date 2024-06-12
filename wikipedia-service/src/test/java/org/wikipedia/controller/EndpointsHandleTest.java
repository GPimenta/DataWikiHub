package org.wikipedia.controller;

import okhttp3.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.wikipedia.model.PageSource;
import org.wikipedia.model.PageSourceWithHTML;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//TODO REMAKE ALL THIS TESTS



public class EndpointsHandleTest {

    private String baseUrl = "https://en.wikipedia.org/w/rest.php/v1/page";
    private String path = "/AI";
    private String pathWithHTML = "/with_html";
    private EndpointsHandle endpointsHandle;
    private OkHttpClient mockClient;
    private Call mockCall;
    private Response mockResponse;
    private ResponseBody mockBody;

    @BeforeEach
    public void setUp() {
        mockClient = mock(OkHttpClient.class);
        mockCall = mock(Call.class);
        mockResponse = mock(Response.class);
        mockBody = mock(ResponseBody.class);
        endpointsHandle = new EndpointsHandle(baseUrl, mockClient);
    }

    @Test
    public void getGenericTest() throws IOException {
        String jsonResponse = "{\"id\":1268,\"key\":\"AI\",\"title\":\"AI\",\"latest\":{\"id\":1219375509,\"timestamp\":\"2024-04-17T11:20:23Z\"},\"content_model\":\"wikitext\",\"license\":{\"url\":\"https://creativecommons.org/licenses/by-sa/4.0/deed.en\",\"title\":\"Creative Commons Attribution-Share Alike 4.0\"},\"source\":\"#REDIRECT [[Artificial intelligence]]\\n\\n{{Redirect category shell|1=\\n{{R from move}}\\n{{R from merge}}\\n{{R from initialism}}\\n{{R hatnote}}\\n{{R printworthy}}\\n}}\",\"redirect_target\":\"/w/rest.php/v1/page/Artificial_intelligence\"}";
        ResponseBody responseBody = ResponseBody.create(jsonResponse, MediaType.parse("application/json"));

        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockResponse.code()).thenReturn(200);
        when(mockResponse.body()).thenReturn(responseBody);
        when(mockBody.string()).thenReturn(jsonResponse);

        Request request = endpointsHandle.createRequest("GET", path);
        Optional<PageSource> result = endpointsHandle.executeRequest(request, PageSource.class);

        assertTrue(result.isPresent());
        PageSource pageSource = result.get();
        assertEquals(1268, pageSource.getId());
        assertEquals("AI", pageSource.getKey());
        assertEquals("AI", pageSource.getTitle());
        assertNotNull(pageSource.getLatest());
        assertEquals(1219375509, pageSource.getLatest().getId());
        assertEquals("2024-04-17T11:20:23Z", pageSource.getLatest().getTimeStamp().format(DateTimeFormatter.ISO_DATE_TIME) + "Z");
        assertEquals("wikitext", pageSource.getContentModel());
        assertNotNull(pageSource.getLicense());
        assertEquals("https://creativecommons.org/licenses/by-sa/4.0/deed.en", pageSource.getLicense().getUrl());
        assertEquals("Creative Commons Attribution-Share Alike 4.0", pageSource.getLicense().getTitle());
        assertEquals("#REDIRECT [[Artificial intelligence]]\n\n{{Redirect category shell|1=\n{{R from move}}\n{{R from merge}}\n{{R from initialism}}\n{{R hatnote}}\n{{R printworthy}}\n}}", pageSource.getSource());
        assertEquals("/w/rest.php/v1/page/Artificial_intelligence", pageSource.getRedirectTarget());
    }

    @Test
    public void getGenericPrintTest() {
        EndpointsHandle endpointsHandle = new EndpointsHandle(baseUrl);
        Request request = endpointsHandle.createRequest("GET", path);
        Optional<PageSource> s = endpointsHandle.executeRequest(request, PageSource.class);
        System.out.println(s.get());
    }

    @Test
    public void getPageSourceTest() throws IOException {

        String jsonResponse = "{\"id\":1268,\"key\":\"AI\",\"title\":\"AI\",\"latest\":{\"id\":1219375509,\"timestamp\":\"2024-04-17T11:20:23Z\"},\"content_model\":\"wikitext\",\"license\":{\"url\":\"https://creativecommons.org/licenses/by-sa/4.0/deed.en\",\"title\":\"Creative Commons Attribution-Share Alike 4.0\"},\"source\":\"#REDIRECT [[Artificial intelligence]]\\n\\n{{Redirect category shell|1=\\n{{R from move}}\\n{{R from merge}}\\n{{R from initialism}}\\n{{R hatnote}}\\n{{R printworthy}}\\n}}\",\"redirect_target\":\"/w/rest.php/v1/page/Artificial_intelligence\"}";
        ResponseBody responseBody = ResponseBody.create(jsonResponse, MediaType.parse("application/json"));

        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);
        when(mockResponse.code()).thenReturn(200);
        when(mockResponse.body()).thenReturn(responseBody);
        when(mockBody.string()).thenReturn(jsonResponse);


        Optional<PageSource> pageSourceOptional = endpointsHandle.getPageSource(path);

        assertTrue(pageSourceOptional.isPresent());
        PageSource pageSource = pageSourceOptional.get();
        assertEquals(1268, pageSource.getId());
        assertEquals("AI", pageSource.getKey());
        assertEquals("AI", pageSource.getTitle());
        assertNotNull(pageSource.getLatest());
        assertEquals(1219375509, pageSource.getLatest().getId());
        assertEquals("2024-04-17T11:20:23Z", pageSource.getLatest().getTimeStamp().format(DateTimeFormatter.ISO_DATE_TIME) + "Z");
        assertEquals("wikitext", pageSource.getContentModel());
        assertNotNull(pageSource.getLicense());
        assertEquals("https://creativecommons.org/licenses/by-sa/4.0/deed.en", pageSource.getLicense().getUrl());
        assertEquals("Creative Commons Attribution-Share Alike 4.0", pageSource.getLicense().getTitle());
        assertEquals("#REDIRECT [[Artificial intelligence]]\n\n{{Redirect category shell|1=\n{{R from move}}\n{{R from merge}}\n{{R from initialism}}\n{{R hatnote}}\n{{R printworthy}}\n}}", pageSource.getSource());
        assertEquals("Redirection exists", pageSource.getRedirectTarget());

//        EndpointsHandle endpointsHandle = new EndpointsHandle(baseUrl);
//        Optional<PageSource> pageSource = endpointsHandle.getPageSource(path);
//        System.out.println(pageSource.get());

    }

    @Test
    public void getPageSourcePrintTest() {
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
