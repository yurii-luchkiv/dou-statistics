package com.forcelate;

import com.forcelate.configuration.Configuration;
import com.forcelate.domain.Category;
import com.forcelate.domain.ProgressState;
import com.forcelate.services.ProgressService;
import com.forcelate.services.ScrapperService;
import com.forcelate.utils.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.forcelate.logger.Logger.debug;

public class Application {
    public static void main(String[] args) {
        try {
            Category category = Configuration.CATEGORY;

            // prepare env
            ProgressService.updateProgress(ProgressState.FOLDERS_ARE_PREPARING);
            FileUtils.prepareFolders();

            ProgressService.updateProgress(ProgressState.DRIVER_ARE_LOADING);
            DriverUtils.load();

            ProgressService.updateProgress(ProgressState.STOP_WORDS_ARE_LOADING);
            StopWordUtils.load();

            ProgressService.updateProgress(ProgressState.SHIT_WORDS_ARE_LOADING);
            ShitWordUtils.load();

            // scrape: URLs
            ProgressService.updateProgress(ProgressState.URL_SCRAPPING);
            List<String> urls = ScrapperService.scrapeURLs(category);

            ProgressService.updateProgress(ProgressState.URL_SAVING);
            FileUtils.saveCategoryUrls(category, urls);

            // scrape: descriptions
            ProgressService.updateProgress(ProgressState.DESCRIPTION_SCRAPPING);
            String descriptions = ScrapperService.scrapeDescriptions(category);

            ProgressService.updateProgress(ProgressState.DESCRIPTION_SAVING);
            FileUtils.saveCategoryDescriptions(category, descriptions);

            // find words
            ProgressService.updateProgress(ProgressState.DESCRIPTION_READING);
            String text = FileUtils.readCategoryDescription(category);

            ProgressService.updateProgress(ProgressState.WORDS_PROCESSING);
            String[] wordsAsArray = text.split(" ");
            List<String> words = Arrays.stream(wordsAsArray)
                    .map(String::toLowerCase)
                    .map(NoiseUtils::leaveOnlyEnglishSymbols)
                    .filter(StopWordUtils::isNotEmptyWord)
                    .filter(StopWordUtils::isNotStopWord)
                    .filter(ShitWordUtils::isNotShitWord)
                    .collect(Collectors.toList());

            ProgressService.updateProgress(ProgressState.WORDS_POPULARITY);
            Map<String, Long> wordsByPopularity = words.stream()
                    .collect(Collectors.groupingBy(
                            Function.identity(),
                            Collectors.counting()
                    ));

            ProgressService.updateProgress(ProgressState.WORDS_SORTING_AND_LIMITING);
            LinkedHashMap<String, Long> sortedWordsByPopularity = wordsByPopularity.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(5)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue, LinkedHashMap::new));

            ProgressService.updateProgress(ProgressState.WORDS_PRINTING);

            debug("===================== RESULTS =====================");
            sortedWordsByPopularity.forEach((key, value) -> debug(key + " => " + value));
            debug("===================== RESULTS =====================");
        } catch (IOException | InterruptedException e) {
            debug("================== (!) WARNING ==================");
            debug("Execution aborted...");
            ProgressService.consoleProgress();
            debug("================== (!) WARNING ==================");
        }
        debug("Completed...");
    }
}
