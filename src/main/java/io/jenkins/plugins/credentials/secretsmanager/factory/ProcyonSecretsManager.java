package io.jenkins.plugins.credentials.secretsmanager.factory;

import com.ai.procyon.jenkins.grpc.agent.*;

public interface ProcyonSecretsManager {

    GetSecretResponse getSecretValue(Integer ID);

    ListSecretsResponse listSecrets(ListSecretsRequest listSecretsRequest) throws InterruptedException;

    CreateSecretResponse createSecret(CreateSecretRequest request);

    DeleteSecretResponse deleteSecret(DeleteSecretRequest request);

    void shutdown();
}
