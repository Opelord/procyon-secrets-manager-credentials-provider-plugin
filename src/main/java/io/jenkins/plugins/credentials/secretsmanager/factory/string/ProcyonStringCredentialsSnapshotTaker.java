package io.jenkins.plugins.credentials.secretsmanager.factory.string;

import com.cloudbees.plugins.credentials.CredentialsSnapshotTaker;
import hudson.Extension;
import hudson.util.Secret;
import io.jenkins.plugins.credentials.secretsmanager.factory.Snapshot;

@Extension
@SuppressWarnings("unused")
public class ProcyonStringCredentialsSnapshotTaker extends CredentialsSnapshotTaker<ProcyonStringCredentials> {
    @Override
    public Class<ProcyonStringCredentials> type() {
        return ProcyonStringCredentials.class;
    }

    @Override
    public ProcyonStringCredentials snapshot(ProcyonStringCredentials credential) {
        return new ProcyonStringCredentials(credential.getId(), credential.getDescription(), new SecretSnapshot(credential.getSecret()));
    }

    private static class SecretSnapshot extends Snapshot<Secret> {
        SecretSnapshot(Secret value) {
            super(value);
        }
    }
}
