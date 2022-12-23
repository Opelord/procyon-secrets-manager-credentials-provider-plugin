package io.jenkins.plugins.credentials.secretsmanager.util;

import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.jvnet.hudson.test.JenkinsRule;

public class Rules {

    public static TestRule jenkinsWithSecretsManager(JenkinsRule jenkins, ProcyonSecretsManagerRule secretsManager) {
        return RuleChain
                .outerRule(secretsManager)
                .around(new DeferredEnvironmentVariables()
                        .set("PROCYON_SERVICE_ENDPOINT", secretsManager::getServiceEndpoint))
                .around(jenkins);
    }
}
