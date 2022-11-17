package io.jenkins.plugins.credentials.secretsmanager.factory.certificate;

import com.cloudbees.plugins.credentials.CredentialsSnapshotTaker;
import com.cloudbees.plugins.credentials.SecretBytes;
import hudson.Extension;
import io.jenkins.plugins.credentials.secretsmanager.factory.Snapshot;

@Extension
@SuppressWarnings("unused")
public class ProcyonCertificateCredentialsSnapshotTaker extends CredentialsSnapshotTaker<ProcyonCertificateCredentials> {
    @Override
    public Class<ProcyonCertificateCredentials> type() {
        return ProcyonCertificateCredentials.class;
    }

    @Override
    public ProcyonCertificateCredentials snapshot(ProcyonCertificateCredentials credential) {
        final SecretBytes result = credential.getSecretBytes();
        return new ProcyonCertificateCredentials(credential.getId(), credential.getDescription(), new SecretBytesSnapshot(result));
    }

    private static class SecretBytesSnapshot extends Snapshot<SecretBytes> {
        private SecretBytesSnapshot(SecretBytes value) {
            super(value);
        }
    }
}
