package io.jenkins.plugins.credentials.secretsmanager.config;

import io.jenkins.plugins.credentials.secretsmanager.config.credentialsProvider.ProcyonCredentialsProvider;

public abstract class ProcyonSyncClientParams extends ClientConfiguration {
    public abstract ProcyonCredentialsProvider getCredentialsProvider();

    public abstract ClientConfiguration getClientConfiguration();
}
