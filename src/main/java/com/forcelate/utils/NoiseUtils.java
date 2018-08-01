package com.forcelate.utils;

import com.forcelate.configuration.Configuration;
import com.forcelate.configuration.Language;

public class NoiseUtils {
    private final static String ONLY_SYMBOLS_EN = "[^A-Za-z]+";
    private final static String ONLY_SYMBOLS_RU = "[^а-яА-ЯЁё]+";

    public static String leaveOnlySymbols(Language language, String text) {
        if (language.isEn()) {
            return text.replaceAll(ONLY_SYMBOLS_EN, "");
        } else if (language.isRu()) {
            return text.replaceAll(ONLY_SYMBOLS_RU, "");
        } else {
            return text;
        }
    }
}
