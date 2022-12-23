package io.jenkins.plugins.credentials.secretsmanager.config;

import com.amazonaws.annotation.NotThreadSafe;
import com.amazonaws.annotation.SdkProtectedApi;

/**
 * Base class for all service specific sync client builders.
 *
 * @param <Subclass>    Concrete builder type, used for better fluent methods.
 * @param <TypeToBuild> Client interface this builder can build.
 */
@NotThreadSafe
@SdkProtectedApi
public abstract class ProcyonSyncClientBuilder<Subclass extends ProcyonSyncClientBuilder, TypeToBuild> extends
        ProcyonClientBuilder<Subclass, TypeToBuild> {
    protected ProcyonSyncClientBuilder(ClientConfigurationFactory clientConfigFactory) {
        super(clientConfigFactory);
    }
    

    @Override
    public final TypeToBuild build() {
        return configureMutableProperties(build(getSyncClientParams(), getEndpoint()));
    }

    /**
     * Overriden by subclasses to call the client constructor.
     *
     * @param clientParams Client Params to create client with
     * @param
     * @return Built client.
     */
    protected abstract TypeToBuild build(ProcyonSyncClientParams clientParams, EndpointConfiguration endpointConfiguration);
}