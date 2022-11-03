package io.jenkins.plugins.credentials.secretsmanager.procyonconfig;

import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.ClientConfigurationFactory;
import io.jenkins.plugins.credentials.secretsmanager.procyonconfig.credentialsProvider.ProcyonCredentials;
import io.jenkins.plugins.credentials.secretsmanager.procyonconfig.credentialsProvider.ProcyonCredentialsProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ProcyonClientBuilder<Subclass extends ProcyonClientBuilder, TypeToBuild> {

    private ProcyonCredentialsProvider credentials;

    private final ClientConfigurationFactory clientConfigFactory;

    private EndpointConfiguration endpointConfiguration;

    protected ProcyonClientBuilder(ClientConfigurationFactory clientConfigFactory) {
        this.clientConfigFactory = clientConfigFactory;
    }

    public final void setCredentials(ProcyonCredentialsProvider credentialsProvider) {
        this.credentials = credentialsProvider;
    }

    public final ProcyonCredentialsProvider getCredentials() {
        return this.credentials;
    }

    final TypeToBuild configureMutableProperties(TypeToBuild clientInterface) {
        ProcyonGoClient client = (ProcyonGoClient) clientInterface;
        client.makeImmutable();
        return clientInterface;
    }

    public final EndpointConfiguration getEndpoint() {
        return endpointConfiguration;
    }

    public final void setEndpointConfiguration(EndpointConfiguration endpointConfiguration) {
        withEndpointConfiguration(endpointConfiguration);
    }

    public final Subclass withEndpointConfiguration(EndpointConfiguration endpointConfiguration) {
        this.endpointConfiguration = endpointConfiguration;
        return getSubclass();
    }

    public static final class EndpointConfiguration {

        private final String serviceEndpoint;
        /**
         * @param serviceEndpoint the service endpoint either with or without the protocol (e.g. https://sns.us-west-1.amazonaws.com or sns.us-west-1.amazonaws.com)
         */
        public EndpointConfiguration(String serviceEndpoint) {
            this.serviceEndpoint = serviceEndpoint;
        }

        public String getServiceEndpoint() {
            return serviceEndpoint;
        }

    }

    @SuppressWarnings("unchecked")
    protected final Subclass getSubclass() {
        return (Subclass) this;
    }
}
