package com.forcelate.utils;

import com.forcelate.configuration.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class ShitWordUtils {

    private final static Map<String, Set<String>> ALPHABET_MAPPING = new LinkedHashMap<>();

    public static void load() throws IOException {
        String fileName;
        if (Configuration.LANGUAGE.isEn()) {
            fileName = "src/main/resources/shitwords_en.txt";
        } else if (Configuration.LANGUAGE.isRu()) {
            fileName = "src/main/resources/shitwords_ru.txt";
        } else {
            throw new RuntimeException("No Language Support: " + Configuration.LANGUAGE);
        }
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(line -> {
                String[] strings = line.split("=");
                String letter = strings[0];
                String wordsAsString = strings[1];
                String[] words = wordsAsString.split(",");
                Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
                ALPHABET_MAPPING.put(letter, uniqueWords);
            });
        }
    }

    public static boolean isNotShitWord(String word) {
        String firstLetter = word.substring(0, 1).toLowerCase();
        return !ALPHABET_MAPPING.get(firstLetter).contains(word);
    }
}
