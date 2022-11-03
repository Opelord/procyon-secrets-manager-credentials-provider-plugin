package io.jenkins.plugins.credentials.secretsmanager.procyonconfig;

import com.amazonaws.ClientConfigurationFactory;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;

public final class ProcyonSecretsManagerClientBuilder extends ProcyonSyncClientBuilder<ProcyonSecretsManagerClientBuilder, ProcyonSecretsManager>{

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
    protected ProcyonSecretsManager build(ProcyonSyncClientParams clientParams) {
        return new ProcyonSecretsManagerClient(clientParams);
    }
}
