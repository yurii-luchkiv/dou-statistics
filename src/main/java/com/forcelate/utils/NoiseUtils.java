package com.forcelate.utils;

public class NoiseUtils {
    private final static String SPECIAL_CHARACTERS = "[-+—.^:,!@#$%&*()?]";
//    private final static String ONLY_SYMBOLS = "[^\\w]";
    // Russian: /[а-яА-ЯЁё]/
    private final static String ONLY_SYMBOLS = "[^A-Za-z]+";

    public static String removeSpecialCharacters(String text) {
        return text.replaceAll(SPECIAL_CHARACTERS, "");
    }

    public static String leaveOnlyEnglishSymbols(String text) {
        return text.replaceAll(ONLY_SYMBOLS, "");
    }
}
