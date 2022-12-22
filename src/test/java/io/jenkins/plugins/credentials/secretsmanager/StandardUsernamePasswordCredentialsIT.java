package io.jenkins.plugins.credentials.secretsmanager;

import com.ai.procyon.jenkins.grpc.agent.CreateSecretRequest;
import com.ai.procyon.jenkins.grpc.agent.CreateSecretResponse;
import com.ai.procyon.jenkins.grpc.agent.SecretValue;
import com.ai.procyon.jenkins.grpc.agent.UsernamePassword;
import com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials;
import com.cloudbees.plugins.credentials.impl.UsernamePasswordCredentialsImpl;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.casc.misc.ConfiguredWithCode;
import io.jenkins.plugins.credentials.secretsmanager.factory.Type;
import io.jenkins.plugins.credentials.secretsmanager.util.*;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import static io.jenkins.plugins.credentials.secretsmanager.util.assertions.CustomAssertions.assertThat;

/**
 * The plugin should support Username With Password credentials.
 */
public class StandardUsernamePasswordCredentialsIT implements CredentialsTests {

    private static final String USERNAME = "joe";
    private static final String PASSWORD = "supersecret";

    public final MyJenkinsConfiguredWithCodeRule jenkins = new MyJenkinsConfiguredWithCodeRule();
    public final ProcyonSecretsManagerRule secretsManager = new ProcyonSecretsManagerRule();

    @Rule
    public final TestRule chain = Rules.jenkinsWithSecretsManager(jenkins, secretsManager);

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportListView() {
        // Given
        final CreateSecretResponse foo = createUsernamePasswordSecret(USERNAME, PASSWORD);

        // When
        final ListBoxModel list = jenkins.getCredentials().list(StandardUsernamePasswordCredentials.class);

        // Then
        assertThat(list)
                .containsOption(foo.getSecret().getName(), foo.getSecret().getName());
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHavePassword() {
        // Given
        final CreateSecretResponse foo = createUsernamePasswordSecret(USERNAME, PASSWORD);

        // When
        final StandardUsernamePasswordCredentials credential =
                jenkins.getCredentials().lookup(StandardUsernamePasswordCredentials.class, foo.getSecret().getName());

        // Then
        assertThat(credential)
                .hasPassword(PASSWORD);
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveUsername() {
        // Given
        final CreateSecretResponse foo = createUsernamePasswordSecret(USERNAME, PASSWORD);

        // When
        final StandardUsernamePasswordCredentials credential =
                jenkins.getCredentials().lookup(StandardUsernamePasswordCredentials.class, foo.getSecret().getName());

        // Then
        assertThat(credential)
                .hasUsername(USERNAME);
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveId() {
        // Given
        final CreateSecretResponse foo = createUsernamePasswordSecret(USERNAME, PASSWORD);

        // When
        final StandardUsernamePasswordCredentials credential =
                jenkins.getCredentials().lookup(StandardUsernamePasswordCredentials.class, foo.getSecret().getName());

        // Then
        assertThat(credential)
                .hasId(foo.getSecret().getName());
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportWithCredentialsBinding() {
        // Given
        final CreateSecretResponse foo = createUsernamePasswordSecret(USERNAME, PASSWORD);

        // When
        final WorkflowRun run = runPipeline("",
                "withCredentials([usernamePassword(credentialsId: '" + foo.getSecret().getName() + "', usernameVariable: 'USR', passwordVariable: 'PSW')]) {",
                "  echo \"Credential: {username: $USR, password: $PSW}\"",
                "}");

        // Then
        assertThat(run)
                .hasResult(hudson.model.Result.SUCCESS)
                .hasLogContaining("Credential: {username: ****, password: ****}");
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportEnvironmentBinding() {
        // Given
        final CreateSecretResponse foo = createUsernamePasswordSecret(USERNAME, PASSWORD);

        // When
        final WorkflowRun run = runPipeline("",
                "pipeline {",
                "  agent none",
                "  stages {",
                "    stage('Example') {",
                "      environment {",
                "        FOO = credentials('" + foo.getSecret().getName() + "')",
                "      }",
                "      steps {",
                "        echo \"{variable: $FOO, username: $FOO_USR, password: $FOO_PSW}\"",
                "      }",
                "    }",
                "  }",
                "}");

        // Then
        assertThat(run)
                .hasResult(hudson.model.Result.SUCCESS)
                .hasLogContaining("{variable: ****, username: ****, password: ****}");
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportSnapshots() {
        // Given
        final CreateSecretResponse foo = createUsernamePasswordSecret(USERNAME, PASSWORD);
        final StandardUsernamePasswordCredentials before = jenkins.getCredentials().lookup(StandardUsernamePasswordCredentials.class, foo.getSecret().getName());

        // When
        final StandardUsernamePasswordCredentials after = CredentialSnapshots.snapshot(before);

        // Then
        assertThat(after)
                .hasUsername(before.getUsername())
                .hasPassword(before.getPassword())
                .hasId(before.getId());
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveDescriptorIcon() {
        final CreateSecretResponse foo = createUsernamePasswordSecret(USERNAME, PASSWORD);
        final StandardUsernamePasswordCredentials ours = jenkins.getCredentials().lookup(StandardUsernamePasswordCredentials.class, foo.getSecret().getName());

        // the default username/password implementation
        final StandardUsernamePasswordCredentials theirs = new UsernamePasswordCredentialsImpl(null, "id", "description", "username", "password");

        assertThat(ours)
                .hasSameDescriptorIconAs(theirs);
    }

    private CreateSecretResponse createUsernamePasswordSecret(String username, String password) {
        final java.util.Map<String,String> tags = new java.util.HashMap<String, String>() {
            {
                put(Type.type, Type.usernamePassword);
                put(Type.username, username);
            }};

        com.ai.procyon.jenkins.grpc.agent.Secret secret = com.ai.procyon.jenkins.grpc.agent.Secret.newBuilder()
                .setName(CredentialNames.random())
                .putAllTags(tags)
                .build();

        UsernamePassword unamepass = UsernamePassword.newBuilder()
                .setPassword(password)
                .setUsername(username)
                .build();

        SecretValue secretValue = SecretValue.newBuilder()
                .setUsernamePassword(unamepass)
                .setSecret(secret)
                .build();

        final CreateSecretRequest request = CreateSecretRequest.newBuilder()
                .setSecretValue(secretValue).build();

        return secretsManager.getClient().createSecret(request);
    }

    private WorkflowRun runPipeline(String... pipeline) {
        return jenkins.getPipelines().run(Strings.m(pipeline));
    }
}
