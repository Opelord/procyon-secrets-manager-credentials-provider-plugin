package io.jenkins.plugins.credentials.secretsmanager.factory.string;

import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.impl.BaseStandardCredentials;
import hudson.Extension;
import hudson.util.Secret;
import io.jenkins.plugins.credentials.secretsmanager.ProcyonCredentialsProvider;
import io.jenkins.plugins.credentials.secretsmanager.Messages;
import io.jenkins.plugins.credentials.secretsmanager.supplier.CredentialsSupplier;
import org.jenkinsci.plugins.plaincredentials.StringCredentials;

import javax.annotation.Nonnull;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class ProcyonStringCredentials extends BaseStandardCredentials implements StringCredentials {

    private final Supplier<Secret> value;

    public ProcyonStringCredentials(String id, String description, Supplier<Secret> value) {
        super(id, description);
        this.value = value;
    }

    @Nonnull
    @Override
    public Secret getSecret() {
        return value.get();
    }

    @Extension
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends BaseStandardCredentialsDescriptor {
        @Override
        @Nonnull
        public String getDisplayName() {
            return Messages.secretText();
        }

        @Override
        public boolean isApplicable(CredentialsProvider provider) {
            return provider instanceof ProcyonCredentialsProvider;
        }
    }
}
