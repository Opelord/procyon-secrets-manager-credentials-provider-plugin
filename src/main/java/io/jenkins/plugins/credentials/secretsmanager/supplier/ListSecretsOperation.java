package io.jenkins.plugins.credentials.secretsmanager.supplier;

import com.ai.procyon.jenkins.grpc.agent.ListSecretsRequest;
import com.ai.procyon.jenkins.grpc.agent.ListSecretsResponse;
import io.jenkins.plugins.credentials.secretsmanager.model.Filter;
import io.jenkins.plugins.credentials.secretsmanager.factory.ProcyonSecretsManager;
import io.jenkins.plugins.credentials.secretsmanager.model.SecretListEntry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Look up all secrets in Secrets Manager using the ListSecrets command. Paginate through secrets
 * until there are none left to get.
 */
class ListSecretsOperation implements Supplier<Collection<SecretListEntry>> {
    //private static final Log LOG = LogFactory.getLog(ProcyonSecretsManager.class.getName());

    private final ProcyonSecretsManager client;

    private final Collection<Filter> filters;

    ListSecretsOperation(ProcyonSecretsManager client, Collection<Filter> filters) {
        this.client = client;
        this.filters = filters;
    }

    @Override
    public Collection<SecretListEntry> get() {
        final List<SecretListEntry> secretList;

        Map<String,String> tags = this.filters.stream()
                .map(entry -> new String[]{entry.getKey(), entry.getValue()})
                .collect(Collectors.toMap(data -> data[0], data -> data[1]));
        ListSecretsRequest request = ListSecretsRequest.newBuilder().putAllTags(tags).build();

        try {
            ListSecretsResponse response = client.listSecrets(request);
            secretList = response.getSecretListList().stream()
                    .map(entry -> new SecretListEntry().withID(entry.getId()).withName(entry.getName()).withTags(entry.getTagsMap()))
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return secretList;
    }
}
