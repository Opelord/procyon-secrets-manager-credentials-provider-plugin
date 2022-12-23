package io.jenkins.plugins.credentials.secretsmanager;

import com.ai.procyon.jenkins.grpc.agent.CreateSecretRequest;
import com.ai.procyon.jenkins.grpc.agent.CreateSecretResponse;
import com.ai.procyon.jenkins.grpc.agent.SecretString;
import com.ai.procyon.jenkins.grpc.agent.SecretValue;
import com.cloudbees.jenkins.plugins.sshcredentials.SSHUserPrivateKey;
import com.cloudbees.jenkins.plugins.sshcredentials.impl.BasicSSHUserPrivateKey;
import com.cloudbees.plugins.credentials.common.StandardCredentials;
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
 * The plugin should support SSH private key credentials.
 */
public class SSHUserPrivateKeyIT implements CredentialsTests {

    private static final String PRIVATE_KEY = Crypto.newPrivateKey();
    private static final String USERNAME = "joe";

    public final MyJenkinsConfiguredWithCodeRule jenkins = new MyJenkinsConfiguredWithCodeRule();
    public final ProcyonSecretsManagerRule secretsManager = new ProcyonSecretsManagerRule();

    @Rule
    public final TestRule chain = Rules.jenkinsWithSecretsManager(jenkins, secretsManager);

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportListView() {
        // Given
        final CreateSecretResponse foo = createSshUserPrivateKeySecret(USERNAME, PRIVATE_KEY);

        // When
        final ListBoxModel list = jenkins.getCredentials().list(SSHUserPrivateKey.class);

        // Then
        assertThat(list)
                .containsOption(foo.getSecret().getName(), foo.getSecret().getName());
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveId() {
        // Given
        final CreateSecretResponse foo = createSshUserPrivateKeySecret(USERNAME, PRIVATE_KEY);

        // When
        final SSHUserPrivateKey credential = lookup(SSHUserPrivateKey.class, foo.getSecret().getName());

        // Then
        assertThat(credential)
                .hasId(foo.getSecret().getName());
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveUsername() {
        // Given
        final CreateSecretResponse foo = createSshUserPrivateKeySecret(USERNAME, PRIVATE_KEY);

        // When
        final SSHUserPrivateKey credential = lookup(SSHUserPrivateKey.class, foo.getSecret().getName());

        // Then
        assertThat(credential)
                .hasUsername(USERNAME);
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHavePrivateKey() {
        // Given
        final CreateSecretResponse foo = createSshUserPrivateKeySecret(USERNAME, PRIVATE_KEY);

        // When
        final SSHUserPrivateKey credential = lookup(SSHUserPrivateKey.class, foo.getSecret().getName());

        // Then
        assertThat(credential)
                .hasPrivateKeys(Lists.of(PRIVATE_KEY));
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveEmptyPassphrase() {
        // Given
        final CreateSecretResponse foo = createSshUserPrivateKeySecret(USERNAME, PRIVATE_KEY);

        // When
        final SSHUserPrivateKey credential = lookup(SSHUserPrivateKey.class, foo.getSecret().getName());

        // Then
        assertThat(credential)
                .doesNotHavePassphrase();
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveDescriptorIcon() {
        final CreateSecretResponse foo = createSshUserPrivateKeySecret(USERNAME, PRIVATE_KEY);
        final SSHUserPrivateKey ours = lookup(SSHUserPrivateKey.class, foo.getSecret().getName());

        final BasicSSHUserPrivateKey theirs = new BasicSSHUserPrivateKey(null, "id", "username", null, "passphrase", "description");

        assertThat(ours)
                .hasSameDescriptorIconAs(theirs);
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportWithCredentialsBinding() {
        // Given
        final CreateSecretResponse foo = createSshUserPrivateKeySecret(USERNAME, PRIVATE_KEY);

        // When
        final WorkflowRun run = runPipeline("",
                "node {",
                "  withCredentials([sshUserPrivateKey(credentialsId: '" + foo.getSecret().getName() + "', keyFileVariable: 'KEYFILE', usernameVariable: 'USERNAME')]) {",
                "    echo \"Credential: {username: $USERNAME, keyFile: $KEYFILE}\"",
                "  }",
                "}");

        // Then
        assertThat(run)
                .hasResult(hudson.model.Result.SUCCESS)
                .hasLogContaining("Credential: {username: ****, keyFile: ****}");
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportEnvironmentBinding() {
        // Given
        final CreateSecretResponse foo = createSshUserPrivateKeySecret(USERNAME, PRIVATE_KEY);

        // When
        final WorkflowRun run = runPipeline("",
                "pipeline {",
                "  agent any",
                "  stages {",
                "    stage('Example') {",
                "      environment {",
                "        FOO = credentials('" + foo.getSecret().getName() + "')",
                "      }",
                "      steps {",
                "        echo \"{variable: $FOO, username: $FOO_USR}\"",
                "      }",
                "    }",
                "  }",
                "}");

        // Then
        assertThat(run)
                .hasResult(hudson.model.Result.SUCCESS)
                .hasLogContaining("{variable: ****, username: ****}");
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportSnapshots() {
        // Given
        final CreateSecretResponse foo = createSshUserPrivateKeySecret(USERNAME, PRIVATE_KEY);
        final SSHUserPrivateKey before = lookup(SSHUserPrivateKey.class, foo.getSecret().getName());

        // When
        final SSHUserPrivateKey after = CredentialSnapshots.snapshot(before);

        // Then
        assertThat(after)
                .hasUsername(before.getUsername())
                .hasPassphrase(before.getPassphrase())
                .hasPrivateKeys(before.getPrivateKeys())
                .hasId(before.getId());
    }

    private CreateSecretResponse createSshUserPrivateKeySecret(String username, String privateKey) {
        final java.util.Map<String,String> tags = new java.util.HashMap<String, String>() {
            {
                put(Type.type, Type.sshUserPrivateKey);
                put(Type.username, username);
            }};

        SecretString newSecretString = SecretString.newBuilder().setValue(privateKey).build();

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

    private <C extends StandardCredentials> C lookup(Class<C> type, String id) {
        return jenkins.getCredentials().lookup(type, id);
    }

    private WorkflowRun runPipeline(String... pipeline) {
        return jenkins.getPipelines().run(Strings.m(pipeline));
    }
}
