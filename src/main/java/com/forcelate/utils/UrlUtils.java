package com.forcelate.utils;

public class UrlUtils {

    public static String getVacancyId(String url) {
        String[] urlAsStrings = url.split("/");
        // NOTE: issue with "?job=hot" in the end
        return urlAsStrings[urlAsStrings.length - 1];
    }
}
