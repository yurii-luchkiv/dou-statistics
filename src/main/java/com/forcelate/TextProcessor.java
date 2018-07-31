package com.forcelate;

import com.forcelate.utils.FileUtils;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TextProcessor {

    public static String getVacanciesText() throws IOException {
        StringBuilder vacanciesTexts = new StringBuilder();
        FileUtils.getVacanciesFiles().forEach(file -> {
            String path = file.getAbsolutePath();
            try (Stream<String> stream = Files.lines(Paths.get(path))) {
                stream.forEach(line -> {
                    // parse HTML => return only text
                    String vacancyClearText = Jsoup.parse(line).text();
                    // TODO: remove non english symbols
                    vacanciesTexts.append(vacancyClearText);
                });
            } catch (IOException e) {
                // TODO
            }
        });
        return vacanciesTexts.toString();
    }
}
