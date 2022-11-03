package io.jenkins.plugins.credentials.secretsmanager.procyonconfig.credentialsProvider;

public interface ProcyonCredentialsProvider {

    public ProcyonCredentials getCredentials();

    public void refresh();

}
