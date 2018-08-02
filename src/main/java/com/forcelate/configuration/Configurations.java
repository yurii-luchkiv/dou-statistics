package com.forcelate.configuration;

import com.forcelate.domain.Category;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.forcelate.logger.Logger.debug;

public class Configurations {

    public static final Configuration ONE = Configuration.builder()
            .category(Category.DEVOPS)
            .language(Language.RU)
            .executionSteps(
                    ConfigurationExecutionSteps.builder()
                            .skipScrapeURLs(true)
                            .skipScrapeDescriptions(true)
                            .build()
            )
            .build();

    public static List<Configuration> getAll() {
        return Stream.of(Category.values()).map(category -> {
            debug("Generating configuration...zzz...");
            return Stream.of(Language.values()).map(language -> {
                debug("Generating configuration...zzz...");
                return Configuration.builder()
                        .category(category)
                        .language(language)
                        .executionSteps(
                                ConfigurationExecutionSteps.builder()
                                        .skipScrapeURLs(true)
                                        .skipScrapeDescriptions(true)
                                        .build()
                        )
                        .build();
            }).collect(Collectors.toList());
        }).flatMap(Collection::stream).collect(Collectors.toList());
    }
}
