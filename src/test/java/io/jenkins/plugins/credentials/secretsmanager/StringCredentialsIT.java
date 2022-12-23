package io.jenkins.plugins.credentials.secretsmanager;

import com.ai.procyon.jenkins.grpc.agent.CreateSecretRequest;
import com.ai.procyon.jenkins.grpc.agent.CreateSecretResponse;
import com.ai.procyon.jenkins.grpc.agent.SecretString;
import com.ai.procyon.jenkins.grpc.agent.SecretValue;
import hudson.util.ListBoxModel;
import hudson.util.Secret;
import io.jenkins.plugins.casc.misc.ConfiguredWithCode;
import io.jenkins.plugins.credentials.secretsmanager.factory.Type;
import io.jenkins.plugins.credentials.secretsmanager.util.*;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;
import org.jenkinsci.plugins.plaincredentials.impl.StringCredentialsImpl;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.Map;

import static io.jenkins.plugins.credentials.secretsmanager.util.assertions.CustomAssertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

/**
 * The plugin should support Secret Text credentials.
 */
public class StringCredentialsIT implements CredentialsTests {

    private static final String SECRET = "supersecret";

    public final MyJenkinsConfiguredWithCodeRule jenkins = new MyJenkinsConfiguredWithCodeRule();
    public final ProcyonSecretsManagerRule secretsManager = new ProcyonSecretsManagerRule();

    @Rule
    public final TestRule chain = Rules.jenkinsWithSecretsManager(jenkins, secretsManager);

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportListView() {
        // Given
        final CreateSecretResponse foo = createStringSecret(SECRET);

        // When
        final ListBoxModel list = jenkins.getCredentials().list(StringCredentials.class);

        // Then
        assertThat(list)
                .containsOption(foo.getSecret().getName(), foo.getSecret().getName());
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveId() {
        // Given
        final CreateSecretResponse foo = createStringSecret(SECRET);

        // When
        final StringCredentials credential = jenkins.getCredentials().lookup(StringCredentials.class, foo.getSecret().getName());

        // Then
        assertThat(credential)
                .hasId(foo.getSecret().getName());
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveSecret() {
        // Given
        final CreateSecretResponse foo = createStringSecret(SECRET);

        // When
        final StringCredentials credential = jenkins.getCredentials().lookup(StringCredentials.class, foo.getSecret().getName());

        // Then
        assertThat(credential)
                .hasSecret(SECRET);
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveDescriptorIcon() {
        final CreateSecretResponse foo = createStringSecret(SECRET);
        final StringCredentials ours = jenkins.getCredentials().lookup(StringCredentials.class, foo.getSecret().getName());

        final StringCredentials theirs = new StringCredentialsImpl(null, "id", "description", Secret.fromString("secret"));

        assertThat(ours)
                .hasSameDescriptorIconAs(theirs);
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportWithCredentialsBinding() {
        // Given
        final CreateSecretResponse foo = createStringSecret(SECRET);

        // When
        final WorkflowRun run = runPipeline("",
                "withCredentials([string(credentialsId: '" + foo.getSecret().getName() + "', variable: 'VAR')]) {",
                "  echo \"Credential: $VAR\"",
                "}");

        // Then
        assertThat(run)
                .hasResult(hudson.model.Result.SUCCESS)
                .hasLogContaining("Credential: ****");
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportEnvironmentBinding() {
        // Given
        final CreateSecretResponse foo = createStringSecret(SECRET);

        // When
        final WorkflowRun run = runPipeline("",
                "pipeline {",
                "  agent none",
                "  stages {",
                "    stage('Example') {",
                "      environment {",
                "        VAR = credentials('" + foo.getSecret().getName() + "')",
                "      }",
                "      steps {",
                "        echo \"{variable: $VAR}\"",
                "      }",
                "    }",
                "  }",
                "}");

        // Then
        assertThat(run)
                .hasResult(hudson.model.Result.SUCCESS)
                .hasLogContaining("{variable: ****}");
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportSnapshots() {
        // Given
        final CreateSecretResponse foo = createStringSecret(SECRET);
        final StringCredentials before = jenkins.getCredentials().lookup(StringCredentials.class, foo.getSecret().getName());

        // When
        final StringCredentials after = CredentialSnapshots.snapshot(before);

        // Then
        assertSoftly(s -> {
            s.assertThat(after.getId()).as("ID").isEqualTo(before.getId());
            s.assertThat(after.getSecret()).as("Secret").isEqualTo(before.getSecret());
        });
    }

    private CreateSecretResponse createStringSecret(String secretString) {
        final Map<String,String> tags = new java.util.HashMap<String, String>() {
            {
                put(Type.type, Type.string);
            }};

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

    private WorkflowRun runPipeline(String... pipeline) {
        return jenkins.getPipelines().run(Strings.m(pipeline));
    }
}
