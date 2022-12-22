package io.jenkins.plugins.credentials.secretsmanager;

import com.ai.procyon.jenkins.grpc.agent.*;
import com.cloudbees.plugins.credentials.SecretBytes;
import com.cloudbees.plugins.credentials.common.StandardCredentials;
import com.google.protobuf.ByteString;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.casc.misc.ConfiguredWithCode;
import io.jenkins.plugins.credentials.secretsmanager.factory.Type;
import io.jenkins.plugins.credentials.secretsmanager.util.*;
import org.jenkinsci.plugins.plaincredentials.FileCredentials;
import org.jenkinsci.plugins.plaincredentials.impl.FileCredentialsImpl;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static io.jenkins.plugins.credentials.secretsmanager.util.assertions.CustomAssertions.assertThat;

/**
 * The plugin should support secret file credentials.
 */
public class FileCredentialsIT implements CredentialsTests {

    private static final String FILENAME = "hello.txt";
    private static final byte[] CONTENT = {0x01, 0x02, 0x03};

    public MyJenkinsConfiguredWithCodeRule jenkins = new MyJenkinsConfiguredWithCodeRule();
    public final ProcyonSecretsManagerRule secretsManager = new ProcyonSecretsManagerRule();

    @Rule
    public final TestRule chain = Rules.jenkinsWithSecretsManager(jenkins, secretsManager);

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveId() {
        // Given
        final CreateSecretResponse foo = createFileSecret(CONTENT);

        // When
        final FileCredentials credential = lookup(FileCredentials.class, foo.getSecret().getName());

        // Then
        assertThat(credential)
                .hasId(foo.getSecret().getName());
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveFileName() {
        // Given
        final CreateSecretResponse foo = createFileSecret(CONTENT);

        // When
        final FileCredentials credential = lookup(FileCredentials.class, foo.getSecret().getName());

        // Then
        assertThat(credential)
                .hasFileName(foo.getSecret().getName());
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveCustomisableFileName() {
        // Given
        final CreateSecretResponse foo = createFileSecret(CONTENT, FILENAME);

        // When
        final FileCredentials credential = lookup(FileCredentials.class, foo.getSecret().getName());

        // Then
        assertThat(credential)
                .hasFileName(FILENAME);
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveContent() {
        // Given
        final CreateSecretResponse foo = createFileSecret(CONTENT);

        // When
        final FileCredentials credential = lookup(FileCredentials.class, foo.getSecret().getName());

        // Then
        assertThat(credential)
                .hasContent(CONTENT);
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveDescriptorIcon() {
        final CreateSecretResponse foo = createFileSecret(CONTENT);
        final FileCredentials ours = lookup(FileCredentials.class, foo.getSecret().getName());

        final FileCredentials theirs = new FileCredentialsImpl(null, "id", "description", "filename", SecretBytes.fromBytes(CONTENT));

        assertThat(ours)
                .hasSameDescriptorIconAs(theirs);
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportListView() {
        // Given
        final CreateSecretResponse foo = createFileSecret(CONTENT);

        // When
        final ListBoxModel list = jenkins.getCredentials().list(FileCredentials.class);

        // Then
        assertThat(list)
                .containsOption(foo.getSecret().getName(), foo.getSecret().getName());
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportWithCredentialsBinding() {
        // Given
        final CreateSecretResponse foo = createFileSecret(CONTENT);

        // When
        final WorkflowRun run = runPipeline("",
                "node {",
                "  withCredentials([file(credentialsId: '" + foo.getSecret().getName() + "', variable: 'FILE')]) {",
                "    echo \"Credential: {fileName: $FILE}\"",
                "  }",
                "}");

        // Then
        assertThat(run)
                .hasResult(hudson.model.Result.SUCCESS)
                .hasLogContaining("Credential: {fileName: ****}");
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportEnvironmentBinding() {
        // Given
        final CreateSecretResponse foo = createFileSecret(CONTENT);

        // When
        final WorkflowRun run = runPipeline("",
                "pipeline {",
                "  agent any",
                "  stages {",
                "    stage('Example') {",
                "      environment {",
                "        VAR = credentials('" + foo.getSecret().getName() + "')",
                "      }",
                "      steps {",
                "        echo \"{filename: $VAR}\"",
                "      }",
                "    }",
                "  }",
                "}");

        // Then
        assertThat(run)
                .hasResult(hudson.model.Result.SUCCESS)
                .hasLogContaining("{filename: ****}");
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportSnapshots() {
        // Given
        final CreateSecretResponse foo = createFileSecret(CONTENT);
        final FileCredentials before = lookup(FileCredentials.class, foo.getSecret().getName());

        // When
        final FileCredentials after = CredentialSnapshots.snapshot(before);

        // Then
        assertThat(after)
                .hasFileName(before.getFileName())
                .hasContent(getContent(before))
                .hasId(before.getId());
    }

    private static InputStream getContent(FileCredentials credentials) {
        try {
            return credentials.getContent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CreateSecretResponse createFileSecret(byte[] content) {
        final java.util.Map<String,String> tags = new java.util.HashMap<String, String>() {
            {
                put(Type.type, Type.file);
            }};

        return createSecret(content,tags);
    }

    private CreateSecretResponse createFileSecret(byte[] content, String filename) {
        final java.util.Map<String,String> tags = new java.util.HashMap<String, String>() {
            {
                put(Type.type, Type.file);
                put(Type.filename, filename);
            }};

        return createSecret(content, tags);
    }

    private CreateSecretResponse createSecret(byte[] content, java.util.Map<String,String> tags) {
        String filename = CredentialNames.random();
        File newFile = File.newBuilder()
                .setFileContent(ByteString.copyFrom(content))
                .setFileName(filename)
                .build();

        com.ai.procyon.jenkins.grpc.agent.Secret secret = com.ai.procyon.jenkins.grpc.agent.Secret.newBuilder()
                .setName(filename)
                .putAllTags(tags)
                .build();
        SecretValue secretValue = SecretValue.newBuilder()
                .setSecret(secret)
                .setFile(newFile)
                .build();

        final CreateSecretRequest request = CreateSecretRequest.newBuilder()
                .setSecretValue(secretValue).build();
        return secretsManager.getClient().createSecret(request);
    }

    private <C extends StandardCredentials> C lookup(Class<C> type, String id) {
        return jenkins.getCredentials().lookup(type, id);
    }

    private WorkflowRun runPipeline(String... pipeline) {
        return jenkins.getPipelines().run(Strings.m(pipeline));
    }
}
