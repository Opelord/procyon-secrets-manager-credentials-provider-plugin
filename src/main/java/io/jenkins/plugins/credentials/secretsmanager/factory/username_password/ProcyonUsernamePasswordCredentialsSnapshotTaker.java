package io.jenkins.plugins.credentials.secretsmanager.factory.username_password;

import com.cloudbees.plugins.credentials.CredentialsSnapshotTaker;
import hudson.Extension;
import hudson.util.Secret;
import io.jenkins.plugins.credentials.secretsmanager.factory.Snapshot;

@Extension
@SuppressWarnings("unused")
public class ProcyonUsernamePasswordCredentialsSnapshotTaker extends CredentialsSnapshotTaker<ProcyonUsernamePasswordCredentials> {
    @Override
    public Class<ProcyonUsernamePasswordCredentials> type() {
        return ProcyonUsernamePasswordCredentials.class;
    }

    @Override
    public ProcyonUsernamePasswordCredentials snapshot(ProcyonUsernamePasswordCredentials credential) {
        return new ProcyonUsernamePasswordCredentials(credential.getId(), credential.getDescription(), new SecretSnapshot(credential.getPassword()), credential.getUsername());
    }

    private static class SecretSnapshot extends Snapshot<Secret> {
        SecretSnapshot(Secret value) {
            super(value);
        }
    }
}

