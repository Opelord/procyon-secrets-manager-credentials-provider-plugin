package io.jenkins.plugins.credentials.secretsmanager.procyonconfig;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import io.jenkins.plugins.credentials.secretsmanager.Messages;
import io.jenkins.plugins.credentials.secretsmanager.procyonconfig.credentialsProvider.CredentialsProvider;
import io.jenkins.plugins.credentials.secretsmanager.procyonconfig.credentialsProvider.DefaultProcyonCredentialsProviderChain;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Objects;

public class Client extends AbstractDescribableImpl<Client> implements Serializable {

    private static final long serialVersionUID = 1L;

    private CredentialsProvider credentialsProvider;

    private EndpointConfiguration endpointConfiguration;

    @DataBoundConstructor
    public Client(CredentialsProvider credentialsProvider, EndpointConfiguration endpointConfiguration, String region) {
        this.credentialsProvider = credentialsProvider;
        this.endpointConfiguration = endpointConfiguration;
    }

    public EndpointConfiguration getEndpointConfiguration() {
        return endpointConfiguration;
    }

    @DataBoundSetter
    public void setEndpointConfiguration(EndpointConfiguration endpointConfiguration) {
        this.endpointConfiguration = endpointConfiguration;
    }

    public CredentialsProvider getCredentialsProvider() {
        return credentialsProvider;
    }

    @DataBoundSetter
    public void setCredentialsProvider(CredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
    }

    public ProcyonSecretsManager build() {
        final ProcyonSecretsManagerClientBuilder builder = ProcyonSecretsManagerClientBuilder.standard();

        if (credentialsProvider != null) {
            builder.setCredentials(credentialsProvider.build());
        }

        if (endpointConfiguration != null) {
            builder.setEndpointConfiguration(endpointConfiguration.build());
        }

        return builder.build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(credentialsProvider, client.credentialsProvider) &&
                Objects.equals(endpointConfiguration, client.endpointConfiguration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(credentialsProvider, endpointConfiguration);
    }

    @Extension
    @Symbol("client")
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends Descriptor<Client> {

        public DefaultProcyonCredentialsProviderChain getDefaultCredentialsProvider() {
            return new DefaultProcyonCredentialsProviderChain();
        }

        @Override
        @Nonnull
        public String getDisplayName() {
            return Messages.client();
        }
    }
}
