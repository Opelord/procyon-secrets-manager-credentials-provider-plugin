package io.jenkins.plugins.credentials.secretsmanager.factory;

import java.net.URI;
import java.net.URISyntaxException;

import com.ai.procyon.jenkins.grpc.agent.ConnectorGrpc;
import com.amazonaws.annotation.SdkProtectedApi;
import io.grpc.*;
import io.jenkins.plugins.credentials.secretsmanager.config.ClientConfiguration;
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

    protected ClientConfiguration clientConfiguration;

    protected ConnectorGrpc.ConnectorBlockingStub blockingStub;

    protected ConnectorGrpc.ConnectorStub nonBlockingStub;

    /** The client configuration */

    ProcyonGoClient(ClientConfiguration clientConfiguration, ManagedChannel channel) {
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
        this.blockingStub = ConnectorGrpc.newBlockingStub(channel);
        this.nonBlockingStub = ConnectorGrpc.newStub(channel);
    }

    /**
     * Endpoint to Procyon-Jenkins agent.
     * @param endpoint
     *            The endpoint or a full URL.
     * @throws IllegalArgumentException
     *             If any problems are detected with the specified endpoint.
     */

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
}
