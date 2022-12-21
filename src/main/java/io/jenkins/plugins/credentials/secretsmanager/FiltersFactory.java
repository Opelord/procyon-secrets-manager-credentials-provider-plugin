package io.jenkins.plugins.credentials.secretsmanager;

import io.jenkins.plugins.credentials.secretsmanager.model.Filter;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class FiltersFactory {

    public static Collection<Filter> create(Collection<io.jenkins.plugins.credentials.secretsmanager.config.Filter> config) {
        if (config == null) {
            return Collections.emptyList();
        }

        return config.stream()
                .map(FiltersFactory::create)
                .collect(Collectors.toList());
    }

    private static Filter create(io.jenkins.plugins.credentials.secretsmanager.config.Filter config) {
        return new Filter()
                .withKey(config.getKey())
                .withValue(config.getValue());
    }
}
