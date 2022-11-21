package io.jenkins.plugins.credentials.secretsmanager.supplier;

import com.amazonaws.services.secretsmanager.model.Filter;
import com.amazonaws.services.secretsmanager.model.Tag;
import com.cloudbees.plugins.credentials.common.StandardCredentials;
import io.jenkins.plugins.credentials.secretsmanager.FiltersFactory;
import io.jenkins.plugins.credentials.secretsmanager.config.Client;
import io.jenkins.plugins.credentials.secretsmanager.config.ListSecrets;
import io.jenkins.plugins.credentials.secretsmanager.config.PluginConfiguration;
import io.jenkins.plugins.credentials.secretsmanager.config.Transformations;
import io.jenkins.plugins.credentials.secretsmanager.factory.CredentialsFactory;
import io.jenkins.plugins.credentials.secretsmanager.factory.ProcyonSecretsManager;
import io.jenkins.plugins.credentials.secretsmanager.model.SecretListEntry;

import java.util.*;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CredentialsSupplier implements Supplier<Collection<StandardCredentials>> {

    private static final Logger LOG = Logger.getLogger(CredentialsSupplier.class.getName());

    private CredentialsSupplier() {

    }

    public static Supplier<Collection<StandardCredentials>> standard() {
        return new CredentialsSupplier();
    }

    @Override
    public Collection<StandardCredentials> get() {

        final PluginConfiguration config = PluginConfiguration.getInstance();

        final Collection<Filter> filters = createListSecretsFilters(config);

        final ProcyonSecretsManager client = createClient(config);

        final ListSecretsOperation listSecretsOperation = new ListSecretsOperation(client, filters);

        LOG.info("Getting secrets list");
        final Collection<SecretListEntry> secretList = listSecretsOperation.get();

        Collection<StandardCredentials> result = secretList.stream()
                .flatMap(secretListEntry -> {
                    final Integer id = secretListEntry.getID();
                    LOG.log(Level.INFO, "Got secret list entry ID = {0}", id);
                    final String name = secretListEntry.getName();
                    final String description = secretListEntry.getDescription();
                    final Map<String, String> tags = Lists.toMap(secretListEntry.getTags(), Tag::getKey, Tag::getValue);
                    LOG.log(Level.INFO, "Got tags in secret list entry {0}", tags);
                    final Optional<StandardCredentials> cred = CredentialsFactory.create(id, name, description, tags, client);
                    LOG.log(Level.INFO, "Credential Supplier: credentials before list: {0}", cred);
                    return Optionals.stream(cred);
                })
                .collect(Collectors.toList());

        LOG.log(Level.INFO, "Got secret list in CredentialSupplier: {0}", result);

        return result;
    }

    private static Collection<Filter> createListSecretsFilters(PluginConfiguration config) {
        final List<io.jenkins.plugins.credentials.secretsmanager.config.Filter> filtersConfig = Optional.ofNullable(config.getListSecrets())
                .map(ListSecrets::getFilters)
                .orElse(Collections.emptyList());

        return FiltersFactory.create(filtersConfig);
    }

    private static ProcyonSecretsManager createClient(PluginConfiguration config) {
        final Client clientConfig = Optional.ofNullable(config.getClient())
                .orElse(new Client(null, null));

        return clientConfig.build();
    }
}
