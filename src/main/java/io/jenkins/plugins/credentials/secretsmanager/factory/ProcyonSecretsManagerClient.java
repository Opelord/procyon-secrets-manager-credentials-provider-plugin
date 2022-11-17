package io.jenkins.plugins.credentials.secretsmanager.factory;

import io.jenkins.plugins.credentials.secretsmanager.config.ClientConfigurationFactory;
import io.jenkins.plugins.credentials.secretsmanager.config.ProcyonSyncClientParams;
import io.jenkins.plugins.credentials.secretsmanager.config.credentialsProvider.ProcyonCredentialsProvider;
import io.jenkins.plugins.credentials.secretsmanager.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.nio.charset.Charset;
import java.util.logging.Level;

public class ProcyonSecretsManagerClient extends ProcyonGoClient implements ProcyonSecretsManager {

    private final ProcyonCredentialsProvider procyonCredentialsProvider;

    private static final Log LOG = LogFactory.getLog(ProcyonSecretsManager.class.getName());

    protected static final ClientConfigurationFactory configFactory = new ClientConfigurationFactory();

    ProcyonSecretsManagerClient(ProcyonSyncClientParams clientParams) {
        super(clientParams);
        this.procyonCredentialsProvider = clientParams.getCredentialsProvider();
        init();
    }

    @Override
    public GetSecretValueResult getSecretValue(GetSecretValueRequest request) {
        LOG.info("Trying to get secret value result");
        String jsonCreds = "";
        byte[] byteCreds = jsonCreds.getBytes(Charset.defaultCharset());
        GetSecretValueResult result = new GetSecretValueResult().withID(1).withName("test-gcp-service-account")
                .withSecretBinary(byteCreds);
        return result;
    }

    @Override
    public ListSecretsResult listSecrets(ListSecretsRequest listSecretsRequest) {
        LOG.info("Trying to list secrets result");
        SecretListEntry secretListEntry = new SecretListEntry().withID(1).withName("test-gcp-service-account");
        java.util.Collection<SecretListEntry> secretList = new java.util.ArrayList<SecretListEntry>(1);
        secretList.add(secretListEntry);
        ListSecretsResult result = new ListSecretsResult().withSecretList((secretList));
        LOG.info(result);
        return result;
    }

    private void init() {

    }
}
