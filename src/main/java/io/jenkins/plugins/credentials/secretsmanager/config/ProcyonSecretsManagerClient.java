package io.jenkins.plugins.credentials.secretsmanager.config;

import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import io.jenkins.plugins.credentials.secretsmanager.config.credentialsProvider.ProcyonCredentialsProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProcyonSecretsManagerClient extends ProcyonGoClient implements ProcyonSecretsManager {

    private final ProcyonCredentialsProvider procyonCredentialsProvider;

    private static final Log log = LogFactory.getLog(ProcyonSecretsManager.class);

    protected static final ClientConfigurationFactory configFactory = new ClientConfigurationFactory();

    ProcyonSecretsManagerClient(ProcyonSyncClientParams clientParams) {
        super(clientParams);
        this.procyonCredentialsProvider = clientParams.getCredentialsProvider();
        init();
    }

    public GetSecretValueResult getSecretValue(GetSecretValueRequest request) {
        return null;
    }

    private void init() {

    }
}
