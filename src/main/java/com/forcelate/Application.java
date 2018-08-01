package com.forcelate;

import com.forcelate.configuration.Configuration;
import com.forcelate.domain.Category;
import com.forcelate.domain.ProgressState;
import com.forcelate.services.ProgressService;
import com.forcelate.services.ScrapperService;
import com.forcelate.utils.*;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;

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
            if (!Configuration.EXECUTION_STEPS.isSkipScrapeURLs()) {
                ProgressService.updateProgress(ProgressState.URL_SCRAPPING);
                List<String> urls = ScrapperService.scrapeURLs(category);

                ProgressService.updateProgress(ProgressState.URL_SAVING);
                FileUtils.saveCategoryUrls(category, urls);
            }

            // scrape: descriptions
            if (!Configuration.EXECUTION_STEPS.isSkipScrapeDescriptions()) {
                ProgressService.updateProgress(ProgressState.DESCRIPTION_SCRAPPING);
                String descriptions = ScrapperService.scrapeDescriptions(category);

                ProgressService.updateProgress(ProgressState.DESCRIPTION_SAVING);
                FileUtils.saveCategoryDescriptions(category, descriptions);
            }

            // find words
            ProgressService.updateProgress(ProgressState.DESCRIPTION_READING);
            String text = FileUtils.readCategoryDescription(category);

            ProgressService.updateProgress(ProgressState.WORDS_PROCESSING);
            String[] wordsAsArray = text.split(" ");

            long[] iteration = { 0 };

            List<String> words = Arrays.stream(wordsAsArray)
                    .map(String::toLowerCase)
                    .map(NoiseUtils::leaveOnlySymbols)
                    .filter(StringUtils::isNotEmptyWord)
                    .filter(StopWordUtils::isNotStopWord)
                    .filter(ShitWordUtils::isNotShitWord)
                    .peek(word -> {
                        if (word.contains("spring")) {
                            iteration[0]++;
                        }
                    })
                    .collect(Collectors.toList());

            debug("===");
            System.out.println("Spring: " + iteration[0]);
            debug("===");

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

            PieChart chart = new PieChartBuilder().width(800).height(600)
                    .title("My Pie Chart")
                    .theme(Styler.ChartTheme.GGPlot2)
                    .build();

            chart.getStyler().setLegendVisible(false);
            chart.getStyler().setAnnotationType(PieStyler.AnnotationType.LabelAndPercentage);
            chart.getStyler().setAnnotationDistance(1.15);
            chart.getStyler().setPlotContentSize(.7);
            chart.getStyler().setStartAngleInDegrees(90);
            sortedWordsByPopularity.forEach(chart::addSeries);
            // new SwingWrapper(chart).displayChart();
            //BitmapEncoder.saveBitmap(chart, "./Sample_Chart", BitmapEncoder.BitmapFormat.PNG);
            BitmapEncoder.saveBitmapWithDPI(chart, "./Sample_Chart_500_DPI", BitmapEncoder.BitmapFormat.PNG, 500);

        } catch (IOException | InterruptedException e) {
            debug("================== (!) WARNING ==================");
            debug("Execution aborted...");
            ProgressService.consoleProgress();
            debug("================== (!) WARNING ==================");
        }
        debug("Completed...");
    }
}
