package io.jenkins.plugins.credentials.secretsmanager.config.credentialsProvider;

public interface ProcyonCredentialsProvider {

    public ProcyonCredentials getCredentials();

    public void refresh();

}
