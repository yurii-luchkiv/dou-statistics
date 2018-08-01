package com.forcelate.configuration;

import com.forcelate.domain.Category;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Configuration {
    private Category category;
    private Language language;
    private ConfigurationExecutionSteps executionSteps;
}
