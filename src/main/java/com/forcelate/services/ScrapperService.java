package com.forcelate.services;

import com.forcelate.domain.Category;
import com.forcelate.utils.FileUtils;
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
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScrapperService {

    public static List<String> scrapeURLs(Category category) {
        // load
        String currentPath = System.getProperty("user.dir");
        String driverPath = currentPath + "/driver/chromedriver";
        System.setProperty("webdriver.chrome.driver", driverPath);
        WebDriver driver = new ChromeDriver();
        driver.get("https://jobs.dou.ua/vacancies/?category=" + category.getValue());

        boolean isMoreJobsButtonDisplayed = findMoreJobsButton(driver).isDisplayed();

        if (isMoreJobsButtonDisplayed) {
            int iteration = 1;
            while (isMoreJobsButtonDisplayed) {
                isMoreJobsButtonDisplayed = findMoreJobsButton(driver).isDisplayed();
                if (isMoreJobsButtonDisplayed) {
                    findMoreJobsButton(driver).click();
                }
                iteration++;
                System.out.println("isDisplayed: " + isMoreJobsButtonDisplayed + ", iteration [" + iteration + "]");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } else {
            // TODO: less than 20 jobs
        }

        List<String> urls = findURLsBySelenium(driver);
        driver.close();

        return urls;
    }

    public static String scrapeDescriptions(Category category) {
        StringBuilder descriptions = new StringBuilder();
        String categoryFilePath = FileUtils.getCategoryFilePath(category);
        try (Stream<String> stream = Files.lines(Paths.get(categoryFilePath))) {
            stream.forEach(url -> {
                try {
                    System.out.println("...");
                    Document document = Jsoup.connect(url).get();
                    Elements elements = document.select(".vacancy-section > p");
                    elements.forEach(element -> {
                        // TODO: add comment
                        descriptions.append(element.text());
                    });
                    descriptions.append("\n");
                } catch (IOException e) {
                    // TODO
                }
            });

        } catch (IOException e) {
            // TODO
        }
        return descriptions.toString();
    }


    // ------------------------------------------------------------------------------------------
    // PRIVATE METHODS
    // ------------------------------------------------------------------------------------------
    private static WebElement findMoreJobsButton(WebDriver driver) {
        return driver.findElement(By.cssSelector(".more-btn > a"));
    }

    private static List<String> findURLsBySelenium(WebDriver driver) {
        return driver.findElements(By.cssSelector("#vacancyListId ul > li .vt"))
                .stream()
                .map(element -> element.getAttribute("href"))
                .collect(Collectors.toList());
    }
}