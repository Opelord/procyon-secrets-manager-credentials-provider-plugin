package io.jenkins.plugins.credentials.secretsmanager.factory;

import io.jenkins.plugins.credentials.secretsmanager.model.ListSecretsRequest;
import io.jenkins.plugins.credentials.secretsmanager.model.ListSecretsResult;
import com.ai.procyon.jenkins.grpc.agent.GetSecretRequest;
import com.ai.procyon.jenkins.grpc.agent.GetSecretResponse;

public interface ProcyonSecretsManager {
    String ENDPOINT_PREFIX = "secretsmanager";

    //CancelRotateSecretResult cancelRotateSecret(CancelRotateSecretRequest cancelRotateSecretRequest);

    //CreateSecretResult createSecret(CreateSecretRequest createSecretRequest);

    //DeleteResourcePolicyResult deleteResourcePolicy(DeleteResourcePolicyRequest deleteResourcePolicyRequest);

    //DeleteSecretResult deleteSecret(DeleteSecretRequest deleteSecretRequest);

    //DescribeSecretResult describeSecret(DescribeSecretRequest describeSecretRequest);

    //GetRandomPasswordResult getRandomPassword(GetRandomPasswordRequest getRandomPasswordRequest);

    //GetResourcePolicyResult getResourcePolicy(GetResourcePolicyRequest getResourcePolicyRequest);

    GetSecretResponse getSecretValue(Integer ID);

    //ListSecretVersionIdsResult listSecretVersionIds(ListSecretVersionIdsRequest listSecretVersionIdsRequest);

    ListSecretsResult listSecrets(ListSecretsRequest listSecretsRequest);

    //PutResourcePolicyResult putResourcePolicy(PutResourcePolicyRequest putResourcePolicyRequest);

    //PutSecretValueResult putSecretValue(PutSecretValueRequest putSecretValueRequest);

    //RemoveRegionsFromReplicationResult removeRegionsFromReplication(RemoveRegionsFromReplicationRequest removeRegionsFromReplicationRequest);

    //ReplicateSecretToRegionsResult replicateSecretToRegions(ReplicateSecretToRegionsRequest replicateSecretToRegionsRequest);

    //RestoreSecretResult restoreSecret(RestoreSecretRequest restoreSecretRequest);

    //RotateSecretResult rotateSecret(RotateSecretRequest rotateSecretRequest);

    //StopReplicationToReplicaResult stopReplicationToReplica(StopReplicationToReplicaRequest stopReplicationToReplicaRequest);

    //TagResourceResult tagResource(TagResourceRequest tagResourceRequest);

    //UntagResourceResult untagResource(UntagResourceRequest untagResourceRequest);

    //UpdateSecretResult updateSecret(UpdateSecretRequest updateSecretRequest);

    //UpdateSecretVersionStageResult updateSecretVersionStage(UpdateSecretVersionStageRequest updateSecretVersionStageRequest);

    //ValidateResourcePolicyResult validateResourcePolicy(ValidateResourcePolicyRequest validateResourcePolicyRequest);

    //void shutdown();

    //ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request);
}
