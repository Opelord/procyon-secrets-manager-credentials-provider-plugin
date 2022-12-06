package io.jenkins.plugins.credentials.secretsmanager.config;

import io.grpc.Channel;
import com.ai.procyon.jenkins.grpc.agent.ConnectorGrpc;
import com.ai.procyon.jenkins.grpc.agent.GetSecretRequest;
import com.ai.procyon.jenkins.grpc.agent.GetSecretResponse;
import io.grpc.StatusRuntimeException;

import java.util.logging.Logger;
import java.util.logging.Level;

public class ProcyonGrpcClient {
    private static final Logger logger = Logger.getLogger(ProcyonGrpcClient.class.getName());

    private ConnectorGrpc.ConnectorBlockingStub blockingStub;

    public ProcyonGrpcClient(Channel channel) {
        blockingStub = ConnectorGrpc.newBlockingStub(channel);
    }

    public GetSecretResponse getSecretValue(Integer ID) {
        logger.log(Level.INFO, "gRPC GetSecretValue invoked");
        GetSecretRequest request = GetSecretRequest.newBuilder().setId(ID).build();
        GetSecretResponse response;
        try {
            response = blockingStub.getSecret(request);
            logger.info("gRPC Response: " + response.getSecret().getType());
            return response;
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "gRPC failed {0}", e.getStatus());
        }

        return null;
    }
}
