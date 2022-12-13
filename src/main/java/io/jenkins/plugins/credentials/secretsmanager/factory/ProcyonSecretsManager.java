package io.jenkins.plugins.credentials.secretsmanager.factory;

import io.jenkins.plugins.credentials.secretsmanager.model.ListSecretsRequest;
import io.jenkins.plugins.credentials.secretsmanager.model.ListSecretsResult;
import com.ai.procyon.jenkins.grpc.agent.GetSecretRequest;
import com.ai.procyon.jenkins.grpc.agent.GetSecretResponse;

public interface ProcyonSecretsManager {
    String ENDPOINT_PREFIX = "secretsmanager";

    GetSecretResponse getSecretValue(Integer ID);

    ListSecretsResult listSecrets(ListSecretsRequest listSecretsRequest);

    void shutdown();
}
