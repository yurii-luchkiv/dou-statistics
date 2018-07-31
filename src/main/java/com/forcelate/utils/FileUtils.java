package com.forcelate.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    public static List<File> getVacanciesFiles() {
        String currentPath = System.getProperty("user.dir");
        String vacanciesPath = currentPath + "/vacancies";
        File vacanciesFolder = new File(vacanciesPath);
        File[] matchingFiles = vacanciesFolder.listFiles((dir, name) -> {
            // add logger messages
            return name.endsWith(".txt");
        });
        if (matchingFiles != null) {
            return Stream.of(matchingFiles).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
