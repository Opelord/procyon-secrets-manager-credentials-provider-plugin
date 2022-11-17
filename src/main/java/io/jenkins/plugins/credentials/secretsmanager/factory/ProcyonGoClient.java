package io.jenkins.plugins.credentials.secretsmanager.factory;

import java.net.URI;
import java.net.URISyntaxException;

import com.amazonaws.annotation.SdkProtectedApi;
import com.amazonaws.client.builder.AwsClientBuilder;
import io.jenkins.plugins.credentials.secretsmanager.config.ClientConfiguration;
import io.jenkins.plugins.credentials.secretsmanager.config.ProcyonHttpClient;
import io.jenkins.plugins.credentials.secretsmanager.config.ProcyonSyncClientParams;
import io.jenkins.plugins.credentials.secretsmanager.config.credentialsProvider.ProcyonCredentialsProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ProcyonGoClient {

    private static final String PROCYON = "Procyon";

    private static final Log log = LogFactory.getLog(ProcyonGoClient.class);

    /**
     * Flag indicating whether a client is mutable or not. Legacy clients built via the constructors
     * are mutable. Clients built with the fluent builders are immutable.
     */
    private volatile boolean isImmutable = false;

    /**
     * The service endpoint to which this client will send requests.
     * <p>
     * Subclass should only read but not assign to this field, at least not
     * without synchronization on the enclosing object for thread-safety
     * reason. If this value is changed to effectively override the endpoint, then the 'isEndpointOverridden' property
     * should also be set to 'true' within the same synchronized block of code.
     */
    protected volatile URI endpoint;

    /**
     * A boolean flag that indicates whether the endpoint has been overridden either on construction or later mutated
     * due to a call to setEndpoint(). If the endpoint property is updated directly then the method doing that update
     * also has the responsibility to update this flag as part of an atomic threadsafe operation.
     */
    protected volatile boolean isEndpointOverridden = false;

    /** The client configuration */
    protected ClientConfiguration clientConfiguration;

    protected ProcyonHttpClient client;

    ProcyonGoClient(ClientConfiguration clientConfiguration) {
        ProcyonSyncClientParams clientParams = new ProcyonSyncClientParams() {
            @Override
            public ProcyonCredentialsProvider getCredentialsProvider() {
                return null;
            }

            @Override
            public ClientConfiguration getClientConfiguration() {
                return clientConfiguration;
            }
        };
        
        this.clientConfiguration = clientParams.getClientConfiguration();

        this.client = new ProcyonHttpClient(clientConfiguration);
    }

    /**
     * Overrides the default endpoint for this client. Callers can use this
     * method to control which AWS region they want to work with.
     * <p>
     * <b>This method is not threadsafe. Endpoints should be configured when the
     * client is created and before any service requests are made. Changing it
     * afterwards creates inevitable race conditions for any service requests in
     * transit.</b>
     * <p>
     * Callers can pass in just the endpoint (ex: "ec2.amazonaws.com") or a full
     * URL, including the protocol (ex: "https://ec2.amazonaws.com"). If the
     * protocol is not specified here, the default protocol from this client's
     * {@link ClientConfiguration} will be used, which by default is HTTPS.
     * <p>
     * For more information on using AWS regions with the AWS SDK for Java, and
     * a complete list of all available endpoints for all AWS services, see:
     * <a href="https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/java-dg-region-selection.html#region-selection-choose-endpoint">
     * https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/java-dg-region-selection.html#region-selection-choose-endpoint</a>
     *
     * @param endpoint
     *            The endpoint (ex: "ec2.amazonaws.com") or a full URL,
     *            including the protocol (ex: "https://ec2.amazonaws.com") of
     *            the region specific AWS endpoint this client will communicate
     *            with.
     * @throws IllegalArgumentException
     *             If any problems are detected with the specified endpoint.
     *
     * @deprecated use {@link AwsClientBuilder#setEndpointConfiguration(AwsClientBuilder.EndpointConfiguration)} for example:
     * {@code builder.setEndpointConfiguration(new EndpointConfiguration(endpoint, signingRegion));}
     */
    @Deprecated
    public void setEndpoint(String endpoint) throws IllegalArgumentException {
        checkMutability();
        URI uri = toURI(endpoint);
        synchronized (this) {
            this.isEndpointOverridden = true;
            this.endpoint = uri;
        }
    }

    /** Returns the endpoint as a URI. */
    private URI toURI(String endpoint) throws IllegalArgumentException {
        if (this.clientConfiguration == null) {
            throw new IllegalArgumentException("ClientConfiguration cannot be null");
        }

        if (endpoint == null) {
            throw new IllegalArgumentException("endpoint cannot be null");
        }

        try {
            return new URI(endpoint);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Internal only API to lock a client's mutable methods. Only intended for use by the fluent
     * builders.
     */

    public final void makeImmutable() {
        this.isImmutable = true;
    }

    /**
     * If the client has been marked as immutable then throw an {@link
     * UnsupportedOperationException}, otherwise do nothing. Should be called by each mutating
     * method.
     */
    @SdkProtectedApi
    protected final void checkMutability() {
        if (isImmutable) {
            throw new UnsupportedOperationException(
                    "Client is immutable when created with the builder.");
        }
    }

    public ClientConfiguration getClientConfiguration() {
        return new ClientConfiguration(clientConfiguration);
    }
}
