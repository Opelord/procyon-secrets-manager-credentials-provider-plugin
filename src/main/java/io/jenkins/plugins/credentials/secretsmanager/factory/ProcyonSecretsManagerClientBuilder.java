package io.jenkins.plugins.credentials.secretsmanager.factory;

import io.jenkins.plugins.credentials.secretsmanager.config.ClientConfigurationFactory;
import io.jenkins.plugins.credentials.secretsmanager.config.ProcyonSyncClientBuilder;
import io.jenkins.plugins.credentials.secretsmanager.config.ProcyonSyncClientParams;

public final class ProcyonSecretsManagerClientBuilder extends ProcyonSyncClientBuilder<ProcyonSecretsManagerClientBuilder, ProcyonSecretsManager> {

    private static final ClientConfigurationFactory CLIENT_CONFIGURATION_FACTORY = new ClientConfigurationFactory();

    public static ProcyonSecretsManagerClientBuilder standard() {
        return new ProcyonSecretsManagerClientBuilder();
    }

    public static ProcyonSecretsManager defaultClient() {
        return standard().build();
    }

    private ProcyonSecretsManagerClientBuilder() {
        super(CLIENT_CONFIGURATION_FACTORY);
    }

    @Override
    protected ProcyonSecretsManager build(ProcyonSyncClientParams clientParams, EndpointConfiguration endpointConfiguration) {
        return new ProcyonSecretsManagerClient(clientParams, endpointConfiguration.getServiceEndpoint());
    }
}
