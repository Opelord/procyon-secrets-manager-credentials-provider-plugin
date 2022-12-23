package io.jenkins.plugins.credentials.secretsmanager;

import com.ai.procyon.jenkins.grpc.agent.CreateSecretRequest;
import com.ai.procyon.jenkins.grpc.agent.CreateSecretResponse;
import com.ai.procyon.jenkins.grpc.agent.SecretString;
import com.ai.procyon.jenkins.grpc.agent.SecretValue;
import com.cloudbees.plugins.credentials.CredentialsScope;
import com.cloudbees.plugins.credentials.CredentialsStore;
import com.cloudbees.plugins.credentials.common.StandardCredentials;
import com.cloudbees.plugins.credentials.domains.Domain;
import hudson.util.ListBoxModel;
import hudson.util.Secret;
import io.jenkins.plugins.casc.misc.ConfiguredWithCode;
import io.jenkins.plugins.credentials.secretsmanager.factory.Type;
import io.jenkins.plugins.credentials.secretsmanager.util.*;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;
import org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

/**
 * The plugin should support CredentialsProvider usage to list available credentials.
 */
public class CredentialsProviderIT {

    private static final String SECRET = "supersecret";

    public final MyJenkinsConfiguredWithCodeRule jenkins = new MyJenkinsConfiguredWithCodeRule();
    public final ProcyonSecretsManagerRule secretsManager = new ProcyonSecretsManagerRule();

    @Rule
    public final TestRule chain = Rules.jenkinsWithSecretsManager(jenkins, secretsManager);

    private CredentialsStore store;

    @Before
    public void setupStore() {
        store = jenkins.getCredentials().lookupStores().iterator().next();
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldStartEmpty() {
        // When
        final List<StringCredentials> credentials = lookup(StringCredentials.class);

        // Then
        assertThat(credentials).isEmpty();
    }

    @Test
    @ConfiguredWithCode(value = "/default.yml")
    public void shouldFailGracefullyWhenSecretsManagerUnavailable() {
        // When
        final List<StringCredentials> credentials = lookup(StringCredentials.class);

        // Then
        assertThat(credentials).isEmpty();
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldUseSecretNameAsCredentialName() {
        // Given
        final CreateSecretResponse foo = createStringSecret(SECRET);

        // When
        final ListBoxModel credentialNames = jenkins.getCredentials().list(StringCredentials.class);

        // Then
        assertThat(credentialNames)
                .extracting("name")
                .containsOnly(foo.getSecret().getName());
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldTolerateUnrelatedTags() {
        // Given
        final java.util.Map<String,String> tags = new java.util.HashMap<String, String>() {
            {
                put(Type.type, Type.string);
                put("foo", "bar");
                put(null, "baz");
                put("qux", null);
            }};

        final CreateSecretResponse foo = createSecret(SECRET, tags);

        // When
        final List<StringCredentials> credentials = lookup(StringCredentials.class);

        // Then
        assertThat(credentials)
                .extracting("id", "secret")
                .containsOnly(tuple(foo.getSecret().getName(), Secret.fromString(SECRET)));
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldNotSupportUpdates() {
        final StringCredentialsImpl credential = new StringCredentialsImpl(CredentialsScope.GLOBAL,"foo", "desc", Secret.fromString(SECRET));

        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> store.updateCredentials(Domain.global(), credential, credential))
                .withMessage("Jenkins may not update credentials in AWS Secrets Manager");
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldNotSupportInserts() {
        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> store.addCredentials(Domain.global(), new StringCredentialsImpl(CredentialsScope.GLOBAL, "foo", "desc", Secret.fromString(SECRET))))
                .withMessage("Jenkins may not add credentials to AWS Secrets Manager");
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldNotSupportDeletes() {
        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> store.removeCredentials(Domain.global(), new StringCredentialsImpl(CredentialsScope.GLOBAL, "foo", "desc", Secret.fromString(SECRET))))
                .withMessage("Jenkins may not remove credentials from AWS Secrets Manager");
    }

    private <C extends StandardCredentials> List<C> lookup(Class<C> type) {
        return jenkins.getCredentials().lookup(type);
    }

    private CreateSecretResponse createStringSecret(String secretString) {
        final java.util.Map<String,String> tags = new java.util.HashMap<String, String>() {
            {
                put(Type.type, Type.string);
            }};

        return createSecret(secretString, tags);
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
