package com.forcelate.configuration;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ConfigurationExecutionSteps {
    private boolean skipScrapeURLs;
    private boolean skipScrapeDescriptions;
}
