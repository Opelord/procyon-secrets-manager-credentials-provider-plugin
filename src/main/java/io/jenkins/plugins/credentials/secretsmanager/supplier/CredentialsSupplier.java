package io.jenkins.plugins.credentials.secretsmanager.supplier;

import io.jenkins.plugins.credentials.secretsmanager.model.Filter;
import com.cloudbees.plugins.credentials.common.StandardCredentials;
import io.jenkins.plugins.credentials.secretsmanager.FiltersFactory;
import io.jenkins.plugins.credentials.secretsmanager.config.*;
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

        Collection<Filter> filters = createListSecretsFilters(config);

        final ProcyonSecretsManager client = createClient(config);

        final ListSecretsOperation listSecretsOperation = new ListSecretsOperation(client, filters);

        LOG.info("Getting secrets list");
        final Collection<SecretListEntry> secretList = listSecretsOperation.get();

        Collection<StandardCredentials> result = secretList.stream()
                .flatMap(secretListEntry -> {
                    final Integer id = secretListEntry.getID();
                    final String name = secretListEntry.getName();
                    final String description = secretListEntry.getDescription();
                    final Map<String, String> tags = secretListEntry.getTags();
                    final Optional<StandardCredentials> cred = CredentialsFactory.create(id, name, description, tags, client);
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
        EndpointConfiguration ec = config.getEndpointConfiguration();
        try {
            final Client clientConfig = Optional.ofNullable(config.getClient())
                    .orElse(new Client(null, ec));
            return clientConfig.build();
        } catch (Exception e) {
            LOG.log(Level.WARNING, "{0}", e.getMessage());
        }
        LOG.log(Level.INFO, "Endpoint provided: {0}", config.getEndpointConfiguration().getServiceEndpoint());
        return null;
    }
}
