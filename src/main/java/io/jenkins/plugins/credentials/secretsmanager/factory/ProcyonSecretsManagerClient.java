package io.jenkins.plugins.credentials.secretsmanager.factory;

import com.ai.procyon.jenkins.grpc.agent.ConnectorGrpc;
import com.ai.procyon.jenkins.grpc.agent.GetSecretRequest;
import com.amazonaws.services.secretsmanager.model.Tag;
import io.grpc.*;
import io.jenkins.plugins.credentials.secretsmanager.config.ClientConfigurationFactory;
import io.jenkins.plugins.credentials.secretsmanager.config.ProcyonSyncClientParams;
import io.jenkins.plugins.credentials.secretsmanager.config.credentialsProvider.ProcyonCredentialsProvider;
import io.jenkins.plugins.credentials.secretsmanager.model.ListSecretsResult;
import io.jenkins.plugins.credentials.secretsmanager.model.ListSecretsRequest;
import io.jenkins.plugins.credentials.secretsmanager.model.SecretListEntry;
import com.ai.procyon.jenkins.grpc.agent.GetSecretResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Formatter;

public class ProcyonSecretsManagerClient extends ProcyonGoClient implements ProcyonSecretsManager {

    private final ProcyonCredentialsProvider procyonCredentialsProvider;

    private static final Log LOG = LogFactory.getLog(ProcyonSecretsManager.class.getName());

    protected static final ClientConfigurationFactory configFactory = new ClientConfigurationFactory();

    ProcyonSecretsManagerClient(ProcyonSyncClientParams clientParams, String serviceEndpoint) {
        super(clientParams, serviceEndpoint);
        this.procyonCredentialsProvider = clientParams.getCredentialsProvider();
        init();
    }

    @Override
    public GetSecretResponse getSecretValue(Integer ID) {
        LOG.info("Trying to get secret value result");

        LOG.info("gRPC GetSecretValue invoked");
        GetSecretRequest request = GetSecretRequest.newBuilder().setId(ID).build();
        GetSecretResponse response;

        ConnectorGrpc.ConnectorBlockingStub blockingStub = ConnectorGrpc.newBlockingStub(channel);
        try {
            response = blockingStub.getSecret(request);
            LOG.info("gRPC Response: " + response.getSecret().getType());
            return response;
        } catch (StatusRuntimeException e) {
            String message = new Formatter().format("gRPC failed %S", e.getStatus()).toString();
            LOG.info(message);
        } finally {
            shutdown();
        }

        return null;
    }

    @Override
    public ListSecretsResult listSecrets(ListSecretsRequest listSecretsRequest) {
        LOG.info("Trying to list secrets result");
        java.util.Collection<SecretListEntry> secretList = new java.util.ArrayList<>();

        com.amazonaws.services.secretsmanager.model.Tag fileNameTag = new Tag().withKey(Tags.filename).withValue("gcp_creds.json");
        com.amazonaws.services.secretsmanager.model.Tag typeTag1 = new Tag().withKey(Tags.type).withValue(Type.file);
        SecretListEntry secretListEntry = new SecretListEntry().withID(1).withName("test-gcp-service-account").withTags(fileNameTag, typeTag1);
        secretList.add(secretListEntry);

        com.amazonaws.services.secretsmanager.model.Tag userNameTag = new Tag().withKey(Tags.username).withValue("Han Solo");
        com.amazonaws.services.secretsmanager.model.Tag typeTag2 = new Tag().withKey(Tags.type).withValue(Type.usernamePassword);
        secretListEntry = new SecretListEntry().withID(2).withName("test-username").withTags(userNameTag, typeTag2);
        secretList.add(secretListEntry);

        com.amazonaws.services.secretsmanager.model.Tag typeTag3 = new Tag().withKey(Tags.type).withValue(Type.certificate);
        secretListEntry = new SecretListEntry().withID(3).withName("test-certificate").withTags(typeTag3);
        secretList.add(secretListEntry);

        ListSecretsResult result = new ListSecretsResult().withSecretList((secretList));
        LOG.info(result);

        return result;
    }

    private void init() {

    }

    public void shutdown() {
        channel.shutdown();
    }
}
