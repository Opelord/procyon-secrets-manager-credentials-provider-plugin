package io.jenkins.plugins.credentials.secretsmanager.config.credentialsProvider;

import hudson.Extension;
import io.jenkins.plugins.credentials.secretsmanager.Messages;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.util.Objects;

public class DefaultProcyonCredentialsProviderChain extends CredentialsProvider implements ProcyonCredentialsProvider {

    private static final DefaultProcyonCredentialsProviderChain INSTANCE
            = new DefaultProcyonCredentialsProviderChain();

    @DataBoundConstructor
    public DefaultProcyonCredentialsProviderChain() {

    }

    @Override
    public ProcyonCredentialsProvider build() {
        return new DefaultProcyonCredentialsProviderChain();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        return getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    @Override
    public ProcyonCredentials getCredentials() {
        //TODO: implement this method
        return null;
    }

    @Override
    public void refresh() {
        //TODO: implement refresh
    }

    @Extension
    @Symbol("default")
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends CredentialsProvider.DescriptorImpl {

        @Override
        @Nonnull
        public String getDisplayName() {
            return Messages.defaultClient();
        }
    }

    public static DefaultProcyonCredentialsProviderChain getInstance() {
        return INSTANCE;
    }
}