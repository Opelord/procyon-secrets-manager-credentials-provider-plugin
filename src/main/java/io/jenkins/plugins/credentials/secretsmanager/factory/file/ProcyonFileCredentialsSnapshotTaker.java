package io.jenkins.plugins.credentials.secretsmanager.factory.file;

import com.cloudbees.plugins.credentials.CredentialsSnapshotTaker;
import com.cloudbees.plugins.credentials.SecretBytes;
import hudson.Extension;
import io.jenkins.plugins.credentials.secretsmanager.factory.Snapshot;

@Extension
@SuppressWarnings("unused")
public class ProcyonFileCredentialsSnapshotTaker extends CredentialsSnapshotTaker<ProcyonFileCredentials> {
    @Override
    public Class<ProcyonFileCredentials> type() {
        return ProcyonFileCredentials.class;
    }

    @Override
    public ProcyonFileCredentials snapshot(ProcyonFileCredentials credential) {
        final SecretBytes content = credential.getContentBytes();
        return new ProcyonFileCredentials(credential.getId(), credential.getDescription(), credential.getFileName(), new SecretBytesSnapshot(content));
    }

    private static class SecretBytesSnapshot extends Snapshot<SecretBytes> {
        private SecretBytesSnapshot(SecretBytes value) {
            super(value);
        }
    }
}
