package io.jenkins.plugins.credentials.secretsmanager.factory.ssh_user_private_key;

import com.cloudbees.plugins.credentials.CredentialsSnapshotTaker;
import hudson.Extension;
import io.jenkins.plugins.credentials.secretsmanager.factory.Snapshot;

@Extension
@SuppressWarnings("unused")
public class ProcyonSshUserPrivateKeySnapshotTaker extends CredentialsSnapshotTaker<ProcyonSshUserPrivateKey> {
    @Override
    public Class<ProcyonSshUserPrivateKey> type() {
        return ProcyonSshUserPrivateKey.class;
    }

    @Override
    public ProcyonSshUserPrivateKey snapshot(ProcyonSshUserPrivateKey credential) {
        return new ProcyonSshUserPrivateKey(credential.getId(), credential.getDescription(), new StringSnapshot(credential.getPrivateKey()), credential.getUsername());
    }

    private static class StringSnapshot extends Snapshot<String> {
        StringSnapshot(String value) {
            super(value);
        }
    }
}

