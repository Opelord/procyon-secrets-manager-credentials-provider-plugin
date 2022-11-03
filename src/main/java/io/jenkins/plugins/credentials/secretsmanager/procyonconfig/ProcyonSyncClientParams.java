package io.jenkins.plugins.credentials.secretsmanager.procyonconfig;

import com.amazonaws.client.builder.AdvancedConfig;
import com.amazonaws.handlers.RequestHandler2;
import com.amazonaws.internal.auth.SignerProvider;
import com.amazonaws.monitoring.CsmConfigurationProvider;
import com.amazonaws.monitoring.MonitoringListener;
import com.amazonaws.retry.RetryPolicyAdapter;
import com.amazonaws.retry.v2.RetryPolicy;
import io.jenkins.plugins.credentials.secretsmanager.procyonconfig.credentialsProvider.ProcyonCredentialsProvider;

import java.net.URI;
import java.util.List;

public abstract class ProcyonSyncClientParams extends ClientConfiguration {
    public abstract ProcyonCredentialsProvider getCredentialsProvider();

    public abstract ClientConfiguration getClientConfiguration();
}
