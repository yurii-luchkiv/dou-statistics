package com.forcelate.configuration;

import com.forcelate.domain.Category;

public class Configuration {
    public static final Category CATEGORY = Category.JAVA;
    public static final Language LANGUAGE = Language.EN;
    public static final int SLEEP_BETWEEN_CLICK_LOAD_MORE = 10000;
    public static final ConfigurationExecutionSteps EXECUTION_STEPS = ConfigurationExecutionSteps.builder()
            .skipScrapeURLs(true)
            .skipScrapeDescriptions(true)
            .build();
}
