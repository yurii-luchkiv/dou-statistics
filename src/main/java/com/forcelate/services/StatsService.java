package com.forcelate.services;

import java.util.LinkedHashMap;

import static com.forcelate.logger.Logger.debug;

public class StatsService {

    public static void console(LinkedHashMap<String, Long> sortedWordsByPopularity) {
        debug("========================================");
        sortedWordsByPopularity.forEach((key, value) -> debug(key + " => " + value));
        debug("========================================");
    }
}
