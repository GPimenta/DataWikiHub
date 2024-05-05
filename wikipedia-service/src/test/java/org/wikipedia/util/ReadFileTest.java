package org.wikipedia.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

public class ReadFileTest {

    @Test
    public void getListTest() throws IOException {
        List<String> list = ReadFile.getList("src/main/java/org/wikipedia/articles/ArticlesList.txt");
        list.forEach(System.out::println);
    }
}
