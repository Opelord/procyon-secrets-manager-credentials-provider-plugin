package io.jenkins.plugins.credentials.secretsmanager;

import com.ai.procyon.jenkins.grpc.agent.CreateSecretRequest;
import com.ai.procyon.jenkins.grpc.agent.CreateSecretResponse;
import com.ai.procyon.jenkins.grpc.agent.File;
import com.ai.procyon.jenkins.grpc.agent.SecretValue;
import com.cloudbees.plugins.credentials.CredentialsUnavailableException;
import com.cloudbees.plugins.credentials.SecretBytes;
import com.cloudbees.plugins.credentials.common.StandardCertificateCredentials;
import com.cloudbees.plugins.credentials.common.StandardCredentials;
import com.cloudbees.plugins.credentials.impl.CertificateCredentialsImpl;
import com.google.protobuf.ByteString;
import hudson.model.Result;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.casc.misc.ConfiguredWithCode;
import io.jenkins.plugins.credentials.secretsmanager.factory.Type;
import io.jenkins.plugins.credentials.secretsmanager.util.*;
import io.jenkins.plugins.credentials.secretsmanager.util.assertions.CustomSoftAssertions;
import org.jenkinsci.plugins.workflow.job.WorkflowRun;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.cert.Certificate;

import static io.jenkins.plugins.credentials.secretsmanager.util.assertions.CustomAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


/**
 * The plugin should support Certificate credentials.
 */
public class StandardCertificateCredentialsIT implements CredentialsTests {

    private static final String ALIAS = "test";
    private static final KeyPair KEY_PAIR = Crypto.newKeyPair();
    private static final char[] PASSWORD = new char[]{};
    private static final String CN = "CN=localhost";
    private static final Certificate[] CERTIFICATE_CHAIN = { Crypto.newSelfSignedCertificate(CN, KEY_PAIR) };

    public final MyJenkinsConfiguredWithCodeRule jenkins = new MyJenkinsConfiguredWithCodeRule();
    public final ProcyonSecretsManagerRule secretsManager = new ProcyonSecretsManagerRule();

    @Rule
    public final TestRule chain = Rules.jenkinsWithSecretsManager(jenkins, secretsManager);

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportListView() {
        // Given
        final KeyStore keyStore = Crypto.singletonKeyStore(ALIAS, KEY_PAIR.getPrivate(), PASSWORD, CERTIFICATE_CHAIN);
        final CreateSecretResponse foo = createCertificateSecret(Crypto.save(keyStore, PASSWORD));

        // When
        final ListBoxModel list = jenkins.getCredentials().list(StandardCertificateCredentials.class);

        // Then
        assertThat(list)
                .containsOption(CN, foo.getSecret().getName());
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveDescriptorIcon() {
        final byte[] keystore = Crypto.save(Crypto.singletonKeyStore(ALIAS, KEY_PAIR.getPrivate(), PASSWORD, CERTIFICATE_CHAIN), PASSWORD);
        final CreateSecretResponse foo = createCertificateSecret(keystore);
        final StandardCertificateCredentials ours = lookup(StandardCertificateCredentials.class, foo.getSecret().getName());

        final StandardCertificateCredentials theirs = new CertificateCredentialsImpl(null, "id", "description", "password", new CertificateCredentialsImpl.UploadedKeyStoreSource(SecretBytes.fromBytes(keystore)));

        assertThat(ours)
                .hasSameDescriptorIconAs(theirs);
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveId() {
        // Given
        final KeyStore keyStore = Crypto.singletonKeyStore(ALIAS, KEY_PAIR.getPrivate(), PASSWORD, CERTIFICATE_CHAIN);
        final CreateSecretResponse foo = createCertificateSecret(Crypto.save(keyStore, PASSWORD));

        // When
        final StandardCertificateCredentials credential = lookup(StandardCertificateCredentials.class, foo.getSecret().getName());

        // Then
        assertThat(credential)
                .hasId(foo.getSecret().getName());
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveEmptyPassword() {
        // Given
        final KeyStore keyStore = Crypto.singletonKeyStore(ALIAS, KEY_PAIR.getPrivate(), PASSWORD, CERTIFICATE_CHAIN);
        final CreateSecretResponse foo = createCertificateSecret(Crypto.save(keyStore, PASSWORD));

        // When
        final StandardCertificateCredentials credential = lookup(StandardCertificateCredentials.class, foo.getSecret().getName());

        // Then
        assertThat(credential)
                .doesNotHavePassword();
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldHaveKeystore() {
        // Given
        final KeyStore keyStore = Crypto.singletonKeyStore(ALIAS, KEY_PAIR.getPrivate(), PASSWORD, CERTIFICATE_CHAIN);
        final CreateSecretResponse foo = createCertificateSecret(Crypto.save(keyStore, PASSWORD));

        // When
        final StandardCertificateCredentials credential = lookup(StandardCertificateCredentials.class, foo.getSecret().getName());

        // Then
        assertThat(credential.getKeyStore())
                .containsEntry(ALIAS, CERTIFICATE_CHAIN);
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportWithCredentialsBinding() {
        // Given
        final KeyStore keyStore = Crypto.singletonKeyStore(ALIAS, KEY_PAIR.getPrivate(), PASSWORD, CERTIFICATE_CHAIN);
        final CreateSecretResponse foo = createCertificateSecret(Crypto.save(keyStore, PASSWORD));

        // When
        final WorkflowRun run = runPipeline("",
                "node {",
                "  withCredentials([certificate(credentialsId: '" + foo.getSecret().getName() + "', keystoreVariable: 'KEYSTORE')]) {",
                "    echo \"Credential: {keystore: $KEYSTORE}\"",
                "  }",
                "}");

        // Then
        assertThat(run)
                .hasResult(Result.SUCCESS)
                .hasLogContaining("Credential: {keystore: ****}");
    }

    @Ignore("Declarative Pipeline does not support certificate bindings")
    public void shouldSupportEnvironmentBinding() {

    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldSupportSnapshots() {
        // Given
        final KeyStore keyStore = Crypto.singletonKeyStore(ALIAS, KEY_PAIR.getPrivate(), PASSWORD, CERTIFICATE_CHAIN);
        final CreateSecretResponse foo = createCertificateSecret(Crypto.save(keyStore, PASSWORD));
        final StandardCertificateCredentials before = lookup(StandardCertificateCredentials.class, foo.getSecret().getName());

        // When
        final StandardCertificateCredentials after = CredentialSnapshots.snapshot(before);

        // Then
        final CustomSoftAssertions s = new CustomSoftAssertions();
        s.assertThat(after).hasId(before.getId());
        s.assertThat(after).hasPassword(before.getPassword());
        s.assertThat(after.getKeyStore()).containsEntry(ALIAS, CERTIFICATE_CHAIN);
        s.assertAll();
    }

    @Test
    @ConfiguredWithCode(value = "/integration.yml")
    public void shouldNotTolerateMalformattedKeyStore() {
        // Given
        final CreateSecretResponse foo = createCertificateSecret(new byte[] {0x00, 0x01});

        // When
        final StandardCertificateCredentials credential = jenkins.getCredentials().lookup(StandardCertificateCredentials.class, foo.getSecret().getName());

        // Then
        assertThatThrownBy(credential::getKeyStore)
                .isInstanceOf(CredentialsUnavailableException.class);
    }

    private CreateSecretResponse createCertificateSecret(byte[] secretBinary) {
        final java.util.Map<String,String> tags = new java.util.HashMap<String, String>() {
            {
                put(Type.type, Type.certificate);
            }};

        com.ai.procyon.jenkins.grpc.agent.Certificate newCert = com.ai.procyon.jenkins.grpc.agent.Certificate.newBuilder()
                .setKeyStore(ByteString.copyFrom(secretBinary))
                .build();

        com.ai.procyon.jenkins.grpc.agent.Secret secret = com.ai.procyon.jenkins.grpc.agent.Secret.newBuilder()
                .setName(CredentialNames.random())
                .putAllTags(tags)
                .build();
        SecretValue secretValue = SecretValue.newBuilder()
                .setSecret(secret)
                .setCertificate(newCert)
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
