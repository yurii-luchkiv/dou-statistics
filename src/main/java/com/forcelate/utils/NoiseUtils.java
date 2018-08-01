package com.forcelate.utils;

import com.forcelate.configuration.Configuration;

public class NoiseUtils {
    private final static String ONLY_SYMBOLS_EN = "[^A-Za-z]+";
    private final static String ONLY_SYMBOLS_RU = "[^а-яА-ЯЁё]+";

    public static String leaveOnlySymbols(String text) {
        if (Configuration.LANGUAGE.isEn()) {
            return text.replaceAll(ONLY_SYMBOLS_EN, "");
        } else if (Configuration.LANGUAGE.isRu()) {
            return text.replaceAll(ONLY_SYMBOLS_RU, "");
        } else {
            return text;
        }
    }
}
