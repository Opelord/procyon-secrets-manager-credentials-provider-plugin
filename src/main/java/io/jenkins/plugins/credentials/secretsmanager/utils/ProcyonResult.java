package io.jenkins.plugins.credentials.secretsmanager.utils;

import com.amazonaws.http.SdkHttpMetadata;

import java.util.Map;

public class ProcyonResult {
    private ResponseMetadata sdkResponseMetadata;

    private SdkHttpMetadata sdkHttpMetadata;

    /**
     * @return The response metadata associated with this request.
     */
    public ResponseMetadata getSdkResponseMetadata() {
        return sdkResponseMetadata;
    }

    public ProcyonResult setSdkResponseMetadata(ResponseMetadata sdkResponseMetadata) {
        this.sdkResponseMetadata = sdkResponseMetadata;
        return this;
    }

    /**
     * @return HTTP related metadata like headers and status code.
     */
    public SdkHttpMetadata getSdkHttpMetadata() {
        return sdkHttpMetadata;
    }

    public ProcyonResult setSdkHttpMetadata(SdkHttpMetadata sdkHttpMetadata) {
        this.sdkHttpMetadata = sdkHttpMetadata;
        return this;
    }

    private static class ResponseMetadata {
        public static final String PROCYON_REQUEST_ID = "PROCYON_REQUEST_ID";

        protected final Map<String, String> metadata;

        /**
         * Creates a new ResponseMetadata object from a specified map of raw
         * metadata information.
         *
         * @param metadata
         *            The raw metadata for the new ResponseMetadata object.
         */
        public ResponseMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
        }

        /**
         * Creates a new ResponseMetadata object from an existing ResponseMetadata
         * object.
         *
         * @param originalResponseMetadata
         *            The ResponseMetadata object from which to create the new
         *            object.
         */
        public ResponseMetadata(ResponseMetadata originalResponseMetadata) {
            this(originalResponseMetadata.metadata);
        }

        /**
         * Returns the Procyon request ID contained in this response metadata object.
         */
        public String getRequestId() {
            return metadata.get(PROCYON_REQUEST_ID);
        }

        @Override
        public String toString() {
            if (metadata == null) return "{}";
            return metadata.toString();
        }
    }
}
