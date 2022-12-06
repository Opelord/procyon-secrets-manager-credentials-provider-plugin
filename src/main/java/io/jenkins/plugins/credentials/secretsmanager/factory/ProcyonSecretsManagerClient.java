package io.jenkins.plugins.credentials.secretsmanager.factory;

import com.amazonaws.services.secretsmanager.model.Tag;
import io.jenkins.plugins.credentials.secretsmanager.config.ClientConfigurationFactory;
import io.jenkins.plugins.credentials.secretsmanager.config.ProcyonSyncClientParams;
import io.jenkins.plugins.credentials.secretsmanager.config.credentialsProvider.ProcyonCredentialsProvider;
import io.jenkins.plugins.credentials.secretsmanager.model.ListSecretsResult;
import io.jenkins.plugins.credentials.secretsmanager.model.ListSecretsRequest;
import io.jenkins.plugins.credentials.secretsmanager.model.SecretListEntry;
import com.ai.procyon.jenkins.grpc.agent.GetSecretResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;
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
    public GetSecretResponse getSecretValue(Integer ID) {
        LOG.info("Trying to get secret value result");
//        try {
//            java.io.File jenkinsGCEJson = new java.io.File("jenkins-gce.json");
//            byte[] byteCreds = org.apache.commons.io.FileUtils.readFileToByteArray(jenkinsGCEJson);
//            return new GetSecretValueResult().withID(1).withName("test-gcp-service-account")
//                    .withSecretBinary(byteCreds);
//        } catch (IOException e) {
//            LOG.info("Couldn't read file", e);
//            return null;
//        }
        return this.client.getSecretValue(ID);
    }

    @Override
    public ListSecretsResult listSecrets(ListSecretsRequest listSecretsRequest) {

        LOG.info("Trying to list secrets result");
        com.amazonaws.services.secretsmanager.model.Tag fileNameTag = new Tag().withKey(Tags.filename).withValue("gcp_creds.json");
        com.amazonaws.services.secretsmanager.model.Tag typeTag = new Tag().withKey(Tags.type).withValue(Type.file);
        SecretListEntry secretListEntry = new SecretListEntry().withID(1).withName("test-gcp-service-account").withTags(fileNameTag, typeTag);
        java.util.Collection<SecretListEntry> secretList = new java.util.ArrayList<SecretListEntry>(1);
        secretList.add(secretListEntry);
        ListSecretsResult result = new ListSecretsResult().withSecretList((secretList));
        LOG.info(result);
        return result;
    }

    private void init() {

    }
}
