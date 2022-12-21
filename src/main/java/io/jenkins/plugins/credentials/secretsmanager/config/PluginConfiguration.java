package io.jenkins.plugins.credentials.secretsmanager.config;

import hudson.Extension;
import jenkins.model.GlobalConfiguration;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.StaplerRequest;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

@Extension
@Symbol("awsCredentialsProvider")
public class PluginConfiguration extends GlobalConfiguration {

    private static final Logger LOG = Logger.getLogger(PluginConfiguration.class.getName());

    /** The Guava cache is never truly turned off, just made very short, as it needs a non-zero cache duration. */
    private static final Duration NO_CACHE = Duration.ofNanos(1);
    private static final Duration DEFAULT_CACHE = Duration.ofSeconds(300);

    /**
     * Whether to cache the credentials or not. By default, credentials are cached for 5 minutes. Caching can be turned off for development purposes.
     */
    private Boolean cache;

    /**
     * Secrets Manager client configuration
     */
    private Client client;

    private transient EndpointConfiguration endpointConfiguration;

    private ListSecrets listSecrets;

    public PluginConfiguration() {
        load();
    }

    public static PluginConfiguration getInstance() {
        return all().get(PluginConfiguration.class);
    }

    public static Duration normalize(Boolean cache) {
        if (cache == null || cache) {
            LOG.config("CredentialsProvider cache enabled");
            return DEFAULT_CACHE;
        } else {
            LOG.config("CredentialsProvider cache disabled");
            return NO_CACHE;
        }
    }

    public Boolean getCache() {
        return cache;
    }

    @DataBoundSetter
    @SuppressWarnings("unused")
    public void setCache(Boolean cache) {
        this.cache = cache;
        save();
    }

    public Client getClient() {
        return client;
    }

    @DataBoundSetter
    @SuppressWarnings("unused")
    public void setClient(Client client) {
        this.client = client;
        save();
    }

    public ListSecrets getListSecrets() {
        return listSecrets;
    }

    @DataBoundSetter
    @SuppressWarnings("unused")
    public void setListSecrets(ListSecrets listSecrets) {
        this.listSecrets = listSecrets;
        save();
    }

    @Override
    public synchronized boolean configure(StaplerRequest req, JSONObject json) {
        // This method is unnecessary, except to apply the following workaround.
        // Workaround: Set any optional struct fields to null before binding configuration.
        // https://groups.google.com/forum/#!msg/jenkinsci-dev/MuRJ-yPRRoo/AvoPZAgbAAAJ
        this.client = null;
        this.listSecrets = null;

        req.bindJSON(this, json);
        save();
        return true;
    }

    public EndpointConfiguration getEndpointConfiguration() {
        return endpointConfiguration;
    }

    public void setEndpointConfiguration(EndpointConfiguration endpointConfiguration) {
        this.endpointConfiguration = endpointConfiguration;
        save();
    }
}
