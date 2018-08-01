package com.forcelate.utils;

import com.forcelate.domain.Category;

public class PathUtils {

    private final static String JOBS_FOLDER = "jobs";
    private final static String DESCRIPTIONS_FOLDER = "descriptions";
    private final static String CHARTS_FOLDER = "charts";

    private final static String EXTENSION = ".txt";

    public static String current() {
        return System.getProperty("user.dir");
    }

    // Driver
    public static String driverPath() {
        return current() + "/driver/chromedriver";
    }

    // Descriptions

    public static String getDescriptionsFolderPath() {
        return current() + "/" + DESCRIPTIONS_FOLDER;
    }

    public static String getDescriptionPath(Category category) {
        return getDescriptionsFolderPath() + "/" + category.getValue() + EXTENSION;
    }

    // Jobs

    public static String getJobsFolderPath() {
        return current() + "/" + JOBS_FOLDER;
    }

    public static String getJobPath(Category category) {
        return getJobsFolderPath() + "/" + category.getValue() + EXTENSION;
    }

    // Charts
    public static String chartsFolderPath() {
        return current() + "/" + CHARTS_FOLDER;
    }

    public static String chartFilePath(String name) {
        return current() + "/" + CHARTS_FOLDER + "/" + name;
    }
}
