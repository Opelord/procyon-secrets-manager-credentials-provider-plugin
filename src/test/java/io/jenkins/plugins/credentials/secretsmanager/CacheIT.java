package io.jenkins.plugins.credentials.secretsmanager;

import com.ai.procyon.jenkins.grpc.agent.CreateSecretRequest;
import com.ai.procyon.jenkins.grpc.agent.CreateSecretResponse;
import com.ai.procyon.jenkins.grpc.agent.SecretString;
import com.ai.procyon.jenkins.grpc.agent.SecretValue;
import io.jenkins.plugins.credentials.secretsmanager.factory.Type;
import io.jenkins.plugins.casc.misc.ConfiguredWithCode;
import io.jenkins.plugins.credentials.secretsmanager.config.Filter;
import io.jenkins.plugins.credentials.secretsmanager.config.ListSecrets;
import io.jenkins.plugins.credentials.secretsmanager.config.PluginConfiguration;
import io.jenkins.plugins.credentials.secretsmanager.util.*;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class CacheIT {

    public final MyJenkinsConfiguredWithCodeRule jenkins = new MyJenkinsConfiguredWithCodeRule();
    public final ProcyonSecretsManagerRule secretsManager = new ProcyonSecretsManagerRule();

    @Rule
    public final TestRule chain = Rules.jenkinsWithSecretsManager(jenkins, secretsManager);

    @Test
    @ConfiguredWithCode("/integration.yml")
    public void shouldCacheCredentialsByDefault() {
        // Given
        final CreateSecretResponse foo = createSecretWithTag("product", "foo");
        final CreateSecretResponse bar = createSecretWithTag("product", "bar");

        // When
        final List<StringCredentials> first = jenkins.getCredentials().lookup(StringCredentials.class);
        // and
        setFilter("product", "foo");
        // and
        final List<StringCredentials> second = jenkins.getCredentials().lookup(StringCredentials.class);

        // Then
        assertSoftly(s -> {
            s.assertThat(first).as("First call").extracting("id").containsOnly(foo.getSecret().getName(), bar.getSecret().getName());
            s.assertThat(second).as("Second call").extracting("id").containsOnly(foo.getSecret().getName(), bar.getSecret().getName());
        });
    }

    @Test
    @ConfiguredWithCode("/cache.yml")
    public void shouldCacheCredentialsWhenEnabled() {
        // Given
        final CreateSecretResponse foo = createSecretWithTag("product", "foo");
        final CreateSecretResponse bar = createSecretWithTag("product", "bar");

        // When
        final List<StringCredentials> first = jenkins.getCredentials().lookup(StringCredentials.class);
        // and
        setFilter("product", "foo");
        // and
        final List<StringCredentials> second = jenkins.getCredentials().lookup(StringCredentials.class);

        // Then
        assertSoftly(s -> {
            s.assertThat(first).as("First call").extracting("id").containsOnly(foo.getSecret().getName(), bar.getSecret().getName());
            s.assertThat(second).as("Second call").extracting("id").containsOnly(foo.getSecret().getName(), bar.getSecret().getName());
        });
    }

    @Test
    @ConfiguredWithCode("/no-cache.yml")
    public void shouldNotCacheCredentialsWhenDisabled() {
        // Given
        final CreateSecretResponse foo = createSecretWithTag("product", "foo");
        final CreateSecretResponse bar = createSecretWithTag("product", "bar");

        // When
        final List<StringCredentials> first = jenkins.getCredentials().lookup(StringCredentials.class);
        // and
        setFilter("product", "foo");
        // and
        final List<StringCredentials> second = jenkins.getCredentials().lookup(StringCredentials.class);

        // Then
        assertSoftly(s -> {
            s.assertThat(first).as("First call").extracting("id").containsOnly(foo.getSecret().getName(), bar.getSecret().getName());
            s.assertThat(second).as("Second call").extracting("id").containsOnly(foo.getSecret().getName());
        });
    }

    private void setFilter(String key, String value) {
        final List<Filter> filters = Lists.of(new Filter(key, value));
        final ListSecrets listSecrets = new ListSecrets(filters);
        final PluginConfiguration config = (PluginConfiguration) jenkins.getInstance().getDescriptor(PluginConfiguration.class);
        config.setListSecrets(listSecrets);
    }

    private CreateSecretResponse createSecretWithTag(String key, String value) {
        Map<String,String> tags = new java.util.HashMap<String, String>() {
            {
                put(Type.type, Type.string);
                put(key,value);
            }};
        return createSecret("supersecret", tags);
    }

    private CreateSecretResponse createSecret(String secretString, Map<String,String> tags) {
        SecretString newSecretString = SecretString.newBuilder().setValue(secretString).build();

        com.ai.procyon.jenkins.grpc.agent.Secret secret = com.ai.procyon.jenkins.grpc.agent.Secret.newBuilder()
                .setName(CredentialNames.random())
                .putAllTags(tags)
                .build();
        SecretValue secretValue = SecretValue.newBuilder()
                .setSecret(secret)
                .setString(newSecretString)
                .build();

        final CreateSecretRequest request = CreateSecretRequest.newBuilder()
                .setSecretValue(secretValue).build();

        return secretsManager.getClient().createSecret(request);
    }

}
