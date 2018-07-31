package com.forcelate;

import com.forcelate.utils.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ScrapperTexts {

    public static void main(String[] args) {
        String fileName = "src/main/resources/unity.txt";
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(url -> {
                try {
                    Document document = Jsoup.connect(url).get();
                    Elements elements = document.select(".vacancy-section > p");
                    elements.forEach(element -> {
                        String[] urlAsStrings = url.split("/");
                        String vacancyId = urlAsStrings[urlAsStrings.length - 1];
                        String description = element.outerHtml();
                        try {
                            FileUtils.saveVacancy(vacancyId, description);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
