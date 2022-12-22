package io.jenkins.plugins.credentials.secretsmanager;

import com.ai.procyon.jenkins.grpc.agent.CreateSecretRequest;
import com.ai.procyon.jenkins.grpc.agent.CreateSecretResponse;
import com.ai.procyon.jenkins.grpc.agent.SecretString;
import com.ai.procyon.jenkins.grpc.agent.SecretValue;
import io.jenkins.plugins.casc.misc.ConfiguredWithCode;
import io.jenkins.plugins.credentials.secretsmanager.factory.Type;
import io.jenkins.plugins.credentials.secretsmanager.util.*;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class FiltersIT {

    public final MyJenkinsConfiguredWithCodeRule jenkins = new MyJenkinsConfiguredWithCodeRule();
    public final ProcyonSecretsManagerRule secretsManager = new ProcyonSecretsManagerRule();

    @Rule
    public final TestRule chain = Rules.jenkinsWithSecretsManager(jenkins, secretsManager);

    @Test
    @ConfiguredWithCode(value = "/filters.yml")
    public void shouldFilterCredentials() {
        // Given
        final CreateSecretResponse foo = createSecretWithTag("product", "foo");
        final CreateSecretResponse bar = createSecretWithTag("product", "bar");

        // When
        final List<StringCredentials> credentials = jenkins.getCredentials().lookup(StringCredentials.class);

        // Then
        assertThat(credentials)
                .extracting("id")
                .contains(foo.getSecret().getName())
                .doesNotContain(bar.getSecret().getName());
    }

    private CreateSecretResponse createSecretWithTag(String key, String value) {
        Map<String,String> tags = new java.util.HashMap<String, String>() {
            {
                put(Type.type, Type.string);
                put(key,value);
            }};
        return createSecret("supersecret", tags);
    }

    private CreateSecretResponse createSecret(String secretString, java.util.Map<String,String> tags) {
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
