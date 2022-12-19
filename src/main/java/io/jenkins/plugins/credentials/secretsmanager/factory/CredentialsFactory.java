package io.jenkins.plugins.credentials.secretsmanager.factory;

import com.ai.procyon.jenkins.grpc.agent.GetSecretResponse;
import com.cloudbees.plugins.credentials.CredentialsUnavailableException;
import com.cloudbees.plugins.credentials.SecretBytes;
import com.cloudbees.plugins.credentials.common.StandardCredentials;
import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.util.Secret;
import io.jenkins.plugins.credentials.secretsmanager.Messages;
import io.jenkins.plugins.credentials.secretsmanager.factory.certificate.ProcyonCertificateCredentials;
import io.jenkins.plugins.credentials.secretsmanager.factory.file.ProcyonFileCredentials;
import io.jenkins.plugins.credentials.secretsmanager.factory.ssh_user_private_key.ProcyonSshUserPrivateKey;
import io.jenkins.plugins.credentials.secretsmanager.factory.string.ProcyonStringCredentials;
import io.jenkins.plugins.credentials.secretsmanager.factory.username_password.ProcyonUsernamePasswordCredentials;
import io.jenkins.plugins.credentials.secretsmanager.model.GetSecretValueRequest;
import io.jenkins.plugins.credentials.secretsmanager.model.GetSecretValueResult;
import io.jenkins.plugins.credentials.secretsmanager.supplier.CredentialsSupplier;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class CredentialsFactory {
    private static final Logger LOG = Logger.getLogger(CredentialsSupplier.class.getName());

    private CredentialsFactory() {

    }

    /**
     * Construct a Jenkins credential from a Secrets Manager secret.
     *
     * @param name the secret's name (must be unique within the Procyon account)
     * @param description the secret's description
     * @param tags the secret's Procyon tags
     * @param client the Secrets Manager client that will retrieve the secret's value on demand
     * @return a credential (if one could be constructed from the secret's properties)
     */
    public static Optional<StandardCredentials> create(Integer id, String name, String description, Map<String, String> tags, ProcyonSecretsManager client) {
        final String type = tags.getOrDefault(Type.type, "");
        final String username = tags.getOrDefault(Type.username, "");
        final String filename = tags.getOrDefault(Type.filename, name);
        LOG.log(Level.INFO, "Got secret of type {0}", type);

        switch (type) {
            case Type.string:
                return Optional.of(new ProcyonStringCredentials(name, description, new SecretSupplier(client, id)));
            case Type.usernamePassword:
                return Optional.of(new ProcyonUsernamePasswordCredentials(name, description, new SecretSupplier(client, id), username));
            case Type.sshUserPrivateKey:
                return Optional.of(new ProcyonSshUserPrivateKey(name, description, new StringSupplier(client, id), username));
            case Type.certificate:
                return Optional.of(new ProcyonCertificateCredentials(name, description, new SecretBytesSupplier(client, id)));
            case Type.file:
                return Optional.of(new ProcyonFileCredentials(name, description, filename, new SecretBytesSupplier(client, id)));
            default:
                return Optional.empty();
        }
    }

    private static class SecretBytesSupplier extends RealSecretsManager implements Supplier<SecretBytes> {

        private static final Logger LOG = Logger.getLogger(SecretBytesSupplier.class.getName());
        private SecretBytesSupplier(ProcyonSecretsManager client, Integer id) {
            super(client, id);
        }

        @Override
        public SecretBytes get() {
            LOG.info("getting secret bytes in SecretBytesSupplier");
            return getSecretValue().match(new SecretValue.Matcher<SecretBytes>() {
                @Override
                public SecretBytes string(String str) {
                    return null;
                }

                @Override
                public SecretBytes binary(byte[] bytes) {
                    return SecretBytes.fromBytes(bytes);
                }
            });
        }
    }

    private static class SecretSupplier extends RealSecretsManager implements Supplier<Secret> {

        private static final Logger LOG = Logger.getLogger(SecretSupplier.class.getName());
        private SecretSupplier(ProcyonSecretsManager client, Integer id) {
            super(client, id);
        }

        @Override
        public Secret get() {
            LOG.info("getting secret in SecretSupplier");
            return getSecretValue().match(new SecretValue.Matcher<Secret>() {
                @Override
                public Secret string(String str) {
                    return Secret.fromString(str);
                }

                @Override
                public Secret binary(byte[] bytes) {
                    return null;
                }
            });
        }
    }

    private static class StringSupplier extends RealSecretsManager implements Supplier<String> {

        private static final Logger LOG = Logger.getLogger(StringSupplier.class.getName());
        private StringSupplier(ProcyonSecretsManager client, Integer id) {
            super(client, id);
        }

        @Override
        public String get() {
            LOG.info("getting string in String Supplier");
            return getSecretValue().match(new SecretValue.Matcher<String>() {
                @Override
                public String string(String str) {
                    return str;
                }

                @Override
                public String binary(byte[] bytes) {
                    return null;
                }
            });
        }
    }

    private static class RealSecretsManager {

        private static final Logger LOG = Logger.getLogger(RealSecretsManager.class.getName());

        private final Integer id;
        private final transient ProcyonSecretsManager client;

        RealSecretsManager(ProcyonSecretsManager client, Integer id) {
            this.client = client;
            this.id = id;
        }

        @NonNull
        SecretValue getSecretValue() {
            try {
                LOG.info("Getting secret");
                final GetSecretResponse response = client.getSecretValue(id);
                if (response == null) {
                    throw new IllegalStateException(Messages.emptySecretError(id));
                }
                LOG.log(Level.WARNING, "got secret? {0}", response.hasSecretValue());

                switch (response.getSecretValue().getSecretContentCase()) {
                    case FILE:
                        return SecretValue.binary(response.getSecretValue().getFile().getFileContent().toByteArray());
                    case CERTIFICATE:
                        return SecretValue.binary(response.getSecretValue().getCertificate().getKeyStore().toByteArray());
                    case SSHUSERPRIVATEKEY:
                        return SecretValue.string(response.getSecretValue().getSshUserPrivateKey().getPrivateKey());
                    case USERNAMEPASSWORD:
                        return SecretValue.string(response.getSecretValue().getUsernamePassword().getPassword());
                    case STRING:
                        return SecretValue.string(response.getSecretValue().getString().getValue());
                }

                throw new IllegalStateException(Messages.emptySecretError(id));
            } catch (IllegalStateException ex) {
                LOG.warning("Procyon Secrets Manager retrieval error");
                LOG.warning(ex.getMessage());

                throw new CredentialsUnavailableException("secret", Messages.couldNotRetrieveCredentialError(id));
            }
        }
    }
}
