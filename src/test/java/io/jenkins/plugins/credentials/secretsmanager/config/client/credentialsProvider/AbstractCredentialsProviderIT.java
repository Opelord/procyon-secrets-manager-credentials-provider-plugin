package io.jenkins.plugins.credentials.secretsmanager.config.client.credentialsProvider;

import io.jenkins.plugins.credentials.secretsmanager.config.Client;
import io.jenkins.plugins.credentials.secretsmanager.config.PluginConfiguration;
import io.jenkins.plugins.credentials.secretsmanager.config.credentialsProvider.DefaultProcyonCredentialsProviderChain;
import io.jenkins.plugins.credentials.secretsmanager.util.assertions.CustomAssertions;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractCredentialsProviderIT {

    protected abstract PluginConfiguration getPluginConfiguration();

    protected abstract void setCredentialsProvider();

    protected abstract void setCredentialsProvider(String roleArn, String roleSessionName);

    protected abstract void setCredentialsProvider(String profileName);

    @Test
    public void shouldSupportDefault() {
        // Given
        setCredentialsProvider();

        // When
        final PluginConfiguration config = getPluginConfiguration();

        // Then (it's allowed to be null or an instance of the default type)
        CustomAssertions.assertThat(Optional.ofNullable(config).map(PluginConfiguration::getClient).map(Client::getCredentialsProvider))
                .isEmptyOrContains(new DefaultProcyonCredentialsProviderChain());
    }
}
