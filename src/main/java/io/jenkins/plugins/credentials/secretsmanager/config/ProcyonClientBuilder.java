package io.jenkins.plugins.credentials.secretsmanager.config;

import io.jenkins.plugins.credentials.secretsmanager.config.credentialsProvider.DefaultProcyonCredentialsProviderChain;
import io.jenkins.plugins.credentials.secretsmanager.config.credentialsProvider.ProcyonCredentialsProvider;
import io.jenkins.plugins.credentials.secretsmanager.factory.ProcyonGoClient;


public abstract class ProcyonClientBuilder<Subclass extends ProcyonClientBuilder, TypeToBuild> {

    private ProcyonCredentialsProvider credentials;

    private final ClientConfigurationFactory clientConfigFactory;

    private ClientConfiguration clientConfig;
    
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

    public final Subclass withCredentials(ProcyonCredentialsProvider credentialsProvider) {
        setCredentials(credentialsProvider);
        return getSubclass();
    }

    final TypeToBuild configureMutableProperties(TypeToBuild clientInterface) {
        ProcyonGoClient client = (ProcyonGoClient) clientInterface;
        client.setEndpoint(endpointConfiguration.serviceEndpoint);
        return clientInterface;
    }

    public abstract TypeToBuild build();

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

    public final ClientConfiguration getClientConfiguration() {
        return this.clientConfig;
    }

    public final void setClientConfiguration(ClientConfiguration config) {
        this.clientConfig = config;
    }

    public final Subclass withClientConfiguration(ClientConfiguration config) {
        setClientConfiguration(config);
        return getSubclass();
    }

    private ClientConfiguration resolveClientConfiguration() {
        return (clientConfig == null) ? clientConfigFactory.getConfig() :
                new ClientConfiguration(clientConfig);
    }
    
    private ProcyonCredentialsProvider resolveCredentials() {
        return (credentials == null) ? DefaultProcyonCredentialsProviderChain.getInstance() : credentials;
    }
    
    protected final ProcyonSyncClientParams getSyncClientParams() {
        return new SyncBuilderParams();
    }
    
    protected class SyncBuilderParams extends ProcyonSyncClientParams {
        private final ClientConfiguration _clientConfig;
        private final ProcyonCredentialsProvider _credentials;
        
        protected SyncBuilderParams() {
            this._clientConfig = resolveClientConfiguration();
            this._credentials = resolveCredentials();
        }

        @Override
        public ProcyonCredentialsProvider getCredentialsProvider() {
            return this._credentials;
        }

        @Override
        public ClientConfiguration getClientConfiguration() {
            return this._clientConfig;
        }
    }

    public static final class EndpointConfiguration {

        private final String serviceEndpoint;

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
