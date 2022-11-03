package io.jenkins.plugins.credentials.secretsmanager.procyonconfig;

import com.amazonaws.ClientConfigurationFactory;
import com.amazonaws.client.builder.AdvancedConfig;
import com.amazonaws.handlers.HandlerChainFactory;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import io.jenkins.plugins.credentials.secretsmanager.procyonconfig.credentialsProvider.ProcyonCredentialsProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProcyonSecretsManagerClient extends ProcyonGoClient {

    private final ProcyonCredentialsProvider procyonCredentialsProvider;

    private static final Log log = LogFactory.getLog(ProcyonSecretsManager.class);

    protected static final ProcyonClientConfigurationFactory configFactory = new ProcyonClientConfigurationFactory();

    ProcyonSecretsManagerClient(ProcyonSyncClientParams clientParams) {
        super(clientParams);
        this.procyonCredentialsProvider = clientParams.getCredentialsProvider();
        init();
    }

    private void init() {

    }
}
