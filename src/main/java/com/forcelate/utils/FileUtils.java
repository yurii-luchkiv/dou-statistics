package com.forcelate.utils;

import com.forcelate.domain.Category;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.forcelate.utils.PathUtils.*;

public class FileUtils {

    public static void prepareFolders() throws IOException {
        // jobs
        Path jobsPath = Paths.get(getJobsFolderPath());
        Files.createDirectories(jobsPath);
        // descriptions
        Path descriptionsPath = Paths.get(getDescriptionsFolderPath());
        Files.createDirectories(descriptionsPath);
    }

    public static void saveCategoryUrls(Category category, List<String> urls) throws IOException {
        String categoryFilePath = getJobPath(category);
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
        String descriptionPath = getDescriptionPath(category);
        BufferedWriter writer = new BufferedWriter(new FileWriter(descriptionPath));
        writer.write(descriptions);
        writer.close();
    }

    public static String readCategoryDescription(Category category) throws IOException {
        String descriptionPath = getDescriptionPath(category);
        BufferedReader reader = new BufferedReader(new FileReader(descriptionPath));
        StringBuilder text = new StringBuilder();
        String currentLine = "";
        while ((currentLine = reader.readLine()) != null) {
            text.append(currentLine);
        }
        return text.toString();
    }

}
