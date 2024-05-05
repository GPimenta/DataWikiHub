package org.wikipedia.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wikipedia.controller.Producer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
    private static final Logger logger = LoggerFactory.getLogger(ReadFile.class.getSimpleName());

    public static List<String> getList(String filePath) throws IOException {
        List<String> articleList = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                articleList.add(line);
            }
        } catch (IOException e) {
            logger.error("\u001B[32m" + "Error reading the file: {}" + "\u001B[0m", e.getMessage());
        }
        return articleList;
    }
}
