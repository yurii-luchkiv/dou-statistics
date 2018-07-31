package com.forcelate;

import com.forcelate.utils.NoiseUtils;
import com.forcelate.utils.StopWordUtils;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) throws IOException {
        // load stop words
        StopWordUtils.load();

        String text = TextProcessor.getVacanciesText();
        String[] wordsAsArray = text.split(" ");
        List<String> words = Arrays.stream(wordsAsArray)
                .map(String::toLowerCase)
                .map(NoiseUtils::leaveOnlyEnglishSymbols)
                .filter(StopWordUtils::isNotEmptyWord)
                .filter(StopWordUtils::isNotStopWord)
                .collect(Collectors.toList());

        Map<String, Long> wordsByPopularity = words.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        LinkedHashMap<String, Long> sortedWordsByPopularity = wordsByPopularity.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        sortedWordsByPopularity.forEach((key, value) -> {
            // add comment
            System.out.println(key + " => " + value);
        });
    }
}
