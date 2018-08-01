package com.forcelate;

import com.forcelate.domain.Category;
import com.forcelate.utils.FileUtils;
import com.forcelate.utils.NoiseUtils;
import com.forcelate.utils.ShitWordUtils;
import com.forcelate.utils.StopWordUtils;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) throws IOException {
        Category category = Category.NODEJS;
//        FileUtils.prepareFolders();
//
//        List<String> urls = ScrapperService.scrapeURLs(category);
//        FileUtils.saveCategoryUrls(category, urls);
//
//        String descriptions = ScrapperService.scrapeDescriptions(category);
//        FileUtils.saveCategoryDescriptions(category, descriptions);

        // load stop words
        StopWordUtils.load();
        ShitWordUtils.load();

        String text = FileUtils.readCategoryText(category);
        String[] wordsAsArray = text.split(" ");
        List<String> words = Arrays.stream(wordsAsArray)
                .map(String::toLowerCase)
                .map(NoiseUtils::leaveOnlyEnglishSymbols)
                .filter(StopWordUtils::isNotEmptyWord)
                .filter(StopWordUtils::isNotStopWord)
                .filter(ShitWordUtils::isNotShitWord)
                .collect(Collectors.toList());

        Map<String, Long> wordsByPopularity = words.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        LinkedHashMap<String, Long> sortedWordsByPopularity = wordsByPopularity.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        sortedWordsByPopularity.forEach((key, value) -> {
            // add comment
            System.out.println(key + " => " + value);
        });
    }
}
