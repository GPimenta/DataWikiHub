package org.wikipedia.util;

import org.wikipedia.controller.EndpointsHandle;

public class GetRandomWord {
    private static final String baseUrl = "https://random-word-form.herokuapp.com";
    private static final String path = "/random/noun";
    static EndpointsHandle endpointsHandle = new EndpointsHandle(baseUrl);

    public static String randomWord() {
        return endpointsHandle.getRandomWordPage(path).orElseThrow(() -> new RuntimeException("Random Word page not working in Service")).getWord().get(0);
    }

}
