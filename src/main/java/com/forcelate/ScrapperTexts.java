package com.forcelate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ScrapperTexts {

    public static void main(String[] args) {
        String path = "/Users/yuriiluchkiv/Development/workspace-l-files/dou-statistics/src/main/resources";
        String fileName = path + "/unity.txt";

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            stream.forEach(url -> {
                try {
                    Document document = Jsoup.connect(url).get();

                    System.out.println("=====");
                    System.out.println("Title: " + document.title());
                    System.out.println("=====");

                    Elements elements = document.select(".vacancy-section > p");
                    System.out.println("=====");
                    System.out.println("Size: " + elements.size());
                    System.out.println("=====");

                    elements.forEach(element -> {
                        System.out.println(element.outerHtml());
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
