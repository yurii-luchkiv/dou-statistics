package com.forcelate.utils;

import com.forcelate.domain.Category;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {
    private final static String JOBS_FOLDER = "jobs";
    private final static String DESCRIPTIONS_FOLDER = "descriptions";

    private final static String EXTENSION = ".txt";

    public static void prepareFolders() throws IOException {
        String currentPath = System.getProperty("user.dir");
        // jobs
        String jobsFolderPath = currentPath + "/" + JOBS_FOLDER;
        Path jobsPath = Paths.get(jobsFolderPath);
        Files.createDirectories(jobsPath);
        // descriptions
        String descriptionsFolderPath = currentPath + "/" + DESCRIPTIONS_FOLDER;
        Path descriptionsPath = Paths.get(descriptionsFolderPath);
        Files.createDirectories(descriptionsPath);
    }

    public static String getCategoryFilePath(Category category) {
        String currentPath = System.getProperty("user.dir");
        return currentPath + "/" + JOBS_FOLDER + "/" + category.getValue() + EXTENSION;
    }

    public static void saveCategoryUrls(Category category, List<String> urls) throws IOException {
        String currentPath = System.getProperty("user.dir");
        String categoryFilePath = currentPath + "/" + JOBS_FOLDER + "/" + category.getValue() + EXTENSION;
        BufferedWriter writer = new BufferedWriter(new FileWriter(categoryFilePath));
        urls.forEach(url -> {
            try {
                writer.write(url);
                writer.newLine();
            } catch (IOException e) {
                // TODO add to progress
            }
        });
        writer.close();
    }

    public static void saveCategoryDescriptions(Category category, String descriptions) throws IOException {
        String currentPath = System.getProperty("user.dir");
        String categoryFilePath = currentPath + "/" + DESCRIPTIONS_FOLDER + "/" + category.getValue() + EXTENSION;
        BufferedWriter writer = new BufferedWriter(new FileWriter(categoryFilePath));
        writer.write(descriptions);
        writer.close();
    }

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

    public static void saveVacancy(String vacancyId, String description) throws IOException {
        String currentPath = System.getProperty("user.dir");
        String vacancyFilePath = currentPath + "/vacancies/" + vacancyId + ".txt";
        System.out.println("Saving vacancy = " + vacancyId + "...");
        BufferedWriter writer = new BufferedWriter(new FileWriter(vacancyFilePath));
        writer.write(description);
        writer.close();
    }
}
