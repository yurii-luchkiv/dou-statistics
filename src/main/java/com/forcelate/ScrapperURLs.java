package com.forcelate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ScrapperURLs {

    public static void main(String[] args) throws IOException {
        findMoreButton();
    }

    private static void findMoreButton() throws IOException {
        String driverPath = "/Users/yuriiluchkiv/Downloads/chromedriver";
        System.out.println("#0");
        System.setProperty("webdriver.chrome.driver", driverPath);
        System.out.println("#1");
        WebDriver driver = new ChromeDriver();
        System.out.println("#2");
//        driver.get("https://jobs.dou.ua/vacancies/?category=Unity");
        driver.get("https://jobs.dou.ua/vacancies/?category=Node.js");
        System.out.println("#3");

        boolean isMoreButtonDisplayed = driver.findElement(By.cssSelector(".more-btn > a")).isDisplayed();
        System.out.println("#4");
        System.out.println("isMoreButtonDisplayed[0]: " + isMoreButtonDisplayed);

        if (isMoreButtonDisplayed) {
            int i = 1;
            while (isMoreButtonDisplayed) {
                WebElement button = driver.findElement(By.cssSelector(".more-btn > a"));
                isMoreButtonDisplayed = button.isDisplayed();
                if (isMoreButtonDisplayed) {
                    button.click();
                }
                i++;
                System.out.println("isDisplayed: " + isMoreButtonDisplayed + ", iteration: " + i);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } else {
            System.out.println("Less than 20");
        }

        System.out.println("====");
        System.out.println("count: " + countURLs(driver));
        System.out.println("====");

        findURLsBySelenium(driver);

        driver.close();


    }

    private static int countURLs(WebDriver driver) {
        return driver.findElements(By.cssSelector("#vacancyListId ul > li .vt")).size();
    }

    private static List<String> findURLsBySelenium(WebDriver driver) {
        return driver.findElements(By.cssSelector("#vacancyListId ul > li .vt"))
                .stream()
                .map(element -> element.getAttribute("href"))
                .peek(url -> {
                    System.out.println(url);
                })
                .collect(Collectors.toList());
    }

    private static void findURLs() throws IOException {
        Document document = Jsoup.connect("https://jobs.dou.ua/vacancies/?category=Java").get();
        System.out.println("=====");
        System.out.println("Title: " + document.title());
        System.out.println("=====");

        Elements elements = document.select("#vacancyListId ul > li .vt");
        System.out.println("=====");
        System.out.println("Size: " + elements.size());
        System.out.println("=====");

        System.out.println("=====");
        Iterator<Element> iterator = elements.iterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            String href = element.attr("href");
            System.out.println("href: " + href);
        }
        System.out.println("=====");
    }
}
