package io.jenkins.plugins.credentials.secretsmanager.util;

import io.jenkins.plugins.credentials.secretsmanager.config.ProcyonClientBuilder;
import io.jenkins.plugins.credentials.secretsmanager.factory.ProcyonSecretsManager;
import io.jenkins.plugins.credentials.secretsmanager.factory.ProcyonSecretsManagerClient;
import io.jenkins.plugins.credentials.secretsmanager.factory.ProcyonSecretsManagerClientBuilder;
import org.junit.rules.ExternalResource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

/**
 * Wraps client-side access to AWS Secrets Manager in tests. Defers client initialization in case you want to set AWS
 * environment variables or Java properties in a wrapper Rule first.
 */
public class ProcyonSecretsManagerRule extends ExternalResource {

    private static final DockerImageName MOTO_IMAGE = DockerImageName.parse("motoserver/moto:2.3.0");

    private final GenericContainer<?> secretsManager = new GenericContainer<>(MOTO_IMAGE)
            .withExposedPorts(5000)
            .waitingFor(Wait.forHttp("/"));

    private transient ProcyonSecretsManager client;

    public String getServiceEndpoint() {
        final String host = secretsManager.getHost();
        final int port = secretsManager.getFirstMappedPort();
        return String.format("%s:%d", host, port);
    }

    public String getHost() {
        return secretsManager.getHost();
    }

    @Override
    public void before() {
        secretsManager.start();

        final String serviceEndpoint = getServiceEndpoint();

        client = ProcyonSecretsManagerClientBuilder.standard()
                .withEndpointConfiguration(new ProcyonClientBuilder.EndpointConfiguration(serviceEndpoint))
                .build();
    }

    @Override
    protected void after() {
        client.shutdown();
        client = null;
        secretsManager.stop();
    }

    public ProcyonSecretsManager getClient() {
        return client;
    }
}