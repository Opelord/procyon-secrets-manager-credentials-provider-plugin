package io.jenkins.plugins.credentials.secretsmanager.factory;

import com.ai.procyon.jenkins.grpc.agent.*;

import io.grpc.*;
import io.jenkins.plugins.credentials.secretsmanager.config.ClientConfigurationFactory;
import io.jenkins.plugins.credentials.secretsmanager.config.ProcyonSyncClientParams;
import io.jenkins.plugins.credentials.secretsmanager.config.credentialsProvider.ProcyonCredentialsProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

public class ProcyonSecretsManagerClient extends ProcyonGoClient implements ProcyonSecretsManager {

    private final ProcyonCredentialsProvider procyonCredentialsProvider;

    private static final Log LOG = LogFactory.getLog(ProcyonSecretsManager.class.getName());

    protected static final ClientConfigurationFactory configFactory = new ClientConfigurationFactory();

    ProcyonSecretsManagerClient(ProcyonSyncClientParams clientParams, ManagedChannel channel) {
        super(clientParams, channel);
        this.procyonCredentialsProvider = clientParams.getCredentialsProvider();
    }

    @Override
    public GetSecretResponse getSecretValue(Integer ID) {
        LOG.info("Trying to get secret value result");

        LOG.info("gRPC GetSecretValue invoked");
        GetSecretRequest request = GetSecretRequest.newBuilder().setId(ID).build();
        GetSecretResponse response;
                
        try {
            response = blockingStub.getSecret(request);
            return response;
        } catch (StatusRuntimeException e) {
            String message = new Formatter().format("gRPC failed %S", e.getStatus()).toString();
            LOG.info(message);
        }

        return null;
    }

    @Override
    public ListSecretsResponse listSecrets(ListSecretsRequest listSecretsRequest) throws InterruptedException {
        LOG.info("Trying to list secrets result");

//        Map<String,String> tagsForRequest = Stream.of(new String[][] {
//                {"Development", "Jenkins plugin"},
//        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
//
//        ListSecretsRequest listSecretsDemoRequest = ListSecretsRequest.newBuilder().putAllTags(tagsForRequest).build();

        ListSecretsResponse response;
        try {
            response = blockingStub.listSecrets(listSecretsRequest);
            return response;

        } catch (StatusRuntimeException e) {
            String message = new Formatter().format("gRPC failed %S", e.getStatus()).toString();
            LOG.info(message);
        }

        return null;
    }
}
