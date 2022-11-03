package io.jenkins.plugins.credentials.secretsmanager.procyonconfig;

import com.amazonaws.ClientConfigurationFactory;
import com.amazonaws.annotation.NotThreadSafe;
import com.amazonaws.annotation.SdkProtectedApi;
import com.amazonaws.annotation.SdkTestInternalApi;
import com.amazonaws.client.AwsSyncClientParams;
import com.amazonaws.regions.AwsRegionProvider;

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
        return configureMutableProperties(build(getSyncClientParams()));
    }

    /**
     * Overriden by subclasses to call the client constructor.
     *
     * @param clientParams Client Params to create client with
     * @return Built client.
     */
    protected abstract TypeToBuild build(AwsSyncClientParams clientParams);

    protected abstract ProcyonSecretsManager build(ProcyonSyncClientParams clientParams);
}