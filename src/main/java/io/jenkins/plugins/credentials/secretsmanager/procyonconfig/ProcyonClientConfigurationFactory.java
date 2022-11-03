package io.jenkins.plugins.credentials.secretsmanager.procyonconfig;

import com.amazonaws.ClientConfiguration;

public class ProcyonClientConfigurationFactory {

    public final ClientConfiguration getConfig() {
        return new ClientConfiguration();
    }

}
