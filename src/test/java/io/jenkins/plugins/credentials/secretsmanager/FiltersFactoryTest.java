package io.jenkins.plugins.credentials.secretsmanager;

import io.jenkins.plugins.credentials.secretsmanager.config.Filter;
import io.jenkins.plugins.credentials.secretsmanager.config.Value;
import io.jenkins.plugins.credentials.secretsmanager.util.Lists;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class FiltersFactoryTest {

    private static Filter filter(String key, String value) {
        return new Filter(key, value);
    }
    @Test
    public void shouldCreateEmptyFilter() {
        assertThat(FiltersFactory.create(null))
                .isEmpty();
    }

    @Test
    public void shouldCreateFilter() {
        final Collection<Filter> config = Lists.of(filter("foo", "bar"));

        assertThat(FiltersFactory.create(config))
                .extracting("key", "value")
                .contains(tuple("foo", "bar"));
    }

    @Test
    public void shouldCreateFilters() {
        final Collection<Filter> config = Lists.of(
                filter("foo", "bar"));

        assertThat(FiltersFactory.create(config))
                .extracting("key", "value")
                .contains(
                        tuple("foo", "bar"));
    }

}
