package com.forcelate.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StopWordUtils {

    public static void main(String[] args) throws IOException {
        String fileName = "src/main/resources/stopwords.txt";
        Map<String, String> alphabetMapping = new LinkedHashMap<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(line -> {
                String[] strings = line.split("=");
                String letter = strings[0];
                String wordsAsString = strings[1];
                alphabetMapping.put(letter, wordsAsString);
            });
        }
        Map<String, String> filteredAlphabetMapping = alphabetMapping.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            String letter = entry.getKey();
                            String wordsAsString = entry.getValue();
                            String[] words = wordsAsString.split(",");
                            Set<String> uniqueWords = new LinkedHashSet<>(Arrays.asList(words));
                            List<String> uniqueWordsAsArray = new ArrayList<>(uniqueWords);
                            Collections.sort(uniqueWordsAsArray);
                            return uniqueWordsAsArray.stream().collect(Collectors.joining(","));
                        }
                ));

        filteredAlphabetMapping.forEach((key, value) -> {
            System.out.println(key + "=" + value);
        });
    }
}
