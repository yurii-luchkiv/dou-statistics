package com.forcelate.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class SkillsUtils {

    private final static Map<String, Set<String>> ALPHABET_MAPPING = new LinkedHashMap<>();

    public static void load() throws IOException {
        String fileName = "src/main/resources/skills/databases.txt";
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

    public static boolean isSkill(String word) {
        String firstLetter = word.substring(0, 1).toLowerCase();
        Set<String> strings = ALPHABET_MAPPING.get(firstLetter);
        return Objects.nonNull(strings) && strings.contains(word);
    }
}
