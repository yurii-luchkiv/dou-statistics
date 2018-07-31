package com.forcelate;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class TextProcessor {

    class ValueComparator implements Comparator {

        Map map;

        public ValueComparator(Map map) {
            this.map = map;
        }

        public int compare(Object keyA, Object keyB) {
            Comparable valueA = (Comparable) map.get(keyA);
            Comparable valueB = (Comparable) map.get(keyB);
            return valueB.compareTo(valueA);
        }
    }

    public void go() {
        String path = "/Users/yuriiluchkiv/Development/workspace-l-files/dou-statistics/src/main/resources";
        String fileName = path + "/description2.txt";

        HashMap<String, Integer> wordCounts = new HashMap<>();

        //read file into stream, try-with-resources
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            System.out.println("=====");
            stream.forEach(line -> {
                //System.out.println("===");
                // System.out.println("line: " + line);
                String clearText = Jsoup.parse(line).text();
                //System.out.println("clearText: " + clearText);
                //System.out.println("===");
                String[] words = clearText.toLowerCase().split(" ");
                Stream.of(words).forEach(word -> {
                    String trimmedWord = word.trim();
                    Integer count = wordCounts.get(trimmedWord);
                    if (count == null) {
                        count = 0;
                    }
                    wordCounts.put(word, count + 1);
                });
            });
            System.out.println("wordCount: " + wordCounts.size());
            System.out.println("=====");



            System.out.println("=====");
            // sort by value
            TreeMap sortedMap = new TreeMap<>(new ValueComparator(wordCounts));
            sortedMap.putAll(wordCounts);
            System.out.println("wordCount (sorted): " + sortedMap.size());
            System.out.println("=====");


            Iterator iterator = sortedMap.entrySet().iterator();
            int count = 0;
            while (iterator.hasNext()) {
                Map.Entry mapEntry = (Map.Entry) iterator.next();
                if (count < 5){
                    System.out.println("Word: " + mapEntry.getKey() + ", times: " + mapEntry.getValue());
                    count++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TextProcessor textProcessor = new TextProcessor();
        textProcessor.go();
    }
}
