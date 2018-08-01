package com.forcelate.services;

import com.forcelate.configuration.Configuration;
import com.forcelate.configuration.Constants;
import com.forcelate.domain.Category;
import com.forcelate.logger.Logger;
import com.forcelate.utils.PathUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.forcelate.configuration.Constants.CssSelectors.MORE_BUTTON;
import static com.forcelate.configuration.Constants.CssSelectors.VACANCIES_URLS;
import static com.forcelate.configuration.Constants.CssSelectors.VACANCY_DESCRIPTIONS;
import static com.forcelate.configuration.Constants.DOU_URL;
import static com.forcelate.logger.Logger.debug;

public class ScrapperService {

    public static List<String> scrapeURLs(Category category) throws InterruptedException {
        // load
        WebDriver driver = new ChromeDriver();
        driver.get(DOU_URL + category.getValue());

        long iteration = 1;
        while (findMoreJobsButton(driver).isDisplayed()) {
            findMoreJobsButton(driver).click();
            iteration++;
            debug("Iteration [ xx / " + iteration * 20 + "]");
            Thread.sleep(Configuration.SLEEP_BETWEEN_CLICK_LOAD_MORE);
        }

        List<String> urls = findURLsBySelenium(driver);
        driver.close();

        return urls;
    }

    public static String scrapeDescriptions(Category category) throws IOException {
        StringBuilder descriptions = new StringBuilder();
        String descriptionFilePath = PathUtils.getDescriptionPath(category);
        long[] iterationArr = { 0 };
        long count = Files.lines(Paths.get(descriptionFilePath)).count();
        try (Stream<String> stream = Files.lines(Paths.get(descriptionFilePath))) {
            stream.forEach(url -> {
                try {
                    long iteration = iterationArr[0];
                    debug("Iteration (descriptions) [ " + iteration + " / " + count + "]");
                    iterationArr[0]++;
                    Document document = Jsoup.connect(url).get();
                    Elements elements = document.select(VACANCY_DESCRIPTIONS);
                    elements.forEach(element -> descriptions.append(element.text()));
                    descriptions.append("\n");
                } catch (IOException e) {
                    ProgressService.addMessage("IOException during scrape descriptions: " + e.getMessage());
                }
            });
        }
        return descriptions.toString();
    }


    // ------------------------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------------------------
    private static WebElement findMoreJobsButton(WebDriver driver) {
        return driver.findElement(By.cssSelector(MORE_BUTTON));
    }

    private static List<String> findURLsBySelenium(WebDriver driver) {
        return driver.findElements(By.cssSelector(VACANCIES_URLS))
                .stream()
                .map(element -> element.getAttribute("href"))
                .collect(Collectors.toList());
    }
}