package io.jenkins.plugins.credentials.secretsmanager.utils;

import io.jenkins.plugins.credentials.secretsmanager.ProcyonCredentialsProvider;

import java.util.*;

public abstract class ProcyonRequest implements Cloneable  {
    public static final ProcyonRequest NOOP = new ProcyonRequest() {
    };

    /**
     * The optional credentials to use for this request - overrides the default credentials set at
     * the client level.
     */
    private ProcyonCredentialsProvider credentialsProvider;

    /**
     * A map of custom header names to header values.
     */
    private Map<String, String> customRequestHeaders;

    /**
     * Custom query parameters for the request.
     */
    private Map<String, List<String>> customQueryParameters;

//    /**
//     * User-defined context for the request.
//     */
//    private transient Map<HandlerContextKey<?>, Object> handlerContext = new HashMap<HandlerContextKey<?>, Object>();

    /**
     * The source object from which the current object was cloned; or null if there isn't one.
     */
    private ProcyonRequest cloneSource;

    private Integer sdkRequestTimeout = null;

    private Integer sdkClientExecutionTimeout = null;

    /**
     * Sets the optional credentials provider to use for this request, overriding the default credentials
     * provider at the client level.
     *
     * @param credentialsProvider
     *            The optional Procyon security credentials provider to use for this request, overriding the
     *            default credentials provider at the client level.
     */
    public void setRequestCredentialsProvider(ProcyonCredentialsProvider credentialsProvider) {
        this.credentialsProvider = credentialsProvider;
    }

    /**
     * Returns the optional credentials provider to use to sign this request, overriding the default
     * credentials provider at the client level.
     *
     * @return The optional credentials provider to use to sign this request, overriding the default
     *         credentials provider at the client level.
     */
    public ProcyonCredentialsProvider getRequestCredentialsProvider() {
        return credentialsProvider;
    }

    /**
     * Sets the optional credentials provider to use for this request, overriding the default credentials
     * provider at the client level.
     *
     * @param credentialsProvider
     *            The optional AWS security credentials provider to use for this request, overriding the
     *            default credentials provider at the client level.
     * @return A reference to this updated object so that method calls can be chained together.
     */
    public <T extends ProcyonRequest> T withRequestCredentialsProvider(final ProcyonCredentialsProvider credentialsProvider) {
        setRequestCredentialsProvider(credentialsProvider);
        @SuppressWarnings("unchecked")
        T t = (T) this;
        return t;
    }

    /**
     * Returns an immutable map of custom header names to header values.
     *
     * @return The immutable map of custom header names to header values.
     */
    public Map<String, String> getCustomRequestHeaders() {
        if (customRequestHeaders == null) {
            return null;
        }
        return Collections.unmodifiableMap(customRequestHeaders);
    }

    /**
     * Put a new custom header to the map of custom header names to custom header values, and return
     * the previous value if the header has already been set in this map.
     * <p>
     * Any custom headers that are defined are used in the HTTP request to the AWS service. These
     * headers will be silently ignored in the event that AWS does not recognize them.
     * <p>
     * NOTE: Custom header values set via this method will overwrite any conflicting values coming
     * from the request parameters.
     *
     * @param name
     *            The name of the header to add
     * @param value
     *            The value of the header to add
     * @return the previous value for the name if it was set, null otherwise
     */
    public String putCustomRequestHeader(String name, String value) {
        if (customRequestHeaders == null) {
            customRequestHeaders = new HashMap<String, String>();
        }
        return customRequestHeaders.put(name, value);
    }

    /**
     * @return the immutable map of custom query parameters. The parameter value is modeled as a
     *         list of strings because multiple values can be specified for the same parameter name.
     */
    public Map<String, List<String>> getCustomQueryParameters() {
        if (customQueryParameters == null) {
            return null;
        }
        return Collections.unmodifiableMap(customQueryParameters);
    }

    /**
     * Add a custom query parameter for the request. Since multiple values are allowed for the same
     * query parameter, this method does NOT overwrite any existing parameter values in the request.
     * <p>
     * Any custom query parameters that are defined are used in the HTTP request to the AWS service.
     *
     * @param name
     *            The name of the query parameter
     * @param value
     *            The value of the query parameter. Only the parameter name will be added in the URI
     *            if the value is set to null. For example, putCustomQueryParameter("param", null)
     *            will be serialized to "?param", while putCustomQueryParameter("param", "") will be
     *            serialized to "?param=".
     */
    public void putCustomQueryParameter(String name, String value) {
        if (customQueryParameters == null) {
            customQueryParameters = new HashMap<String, List<String>>();
        }
        List<String> paramList = customQueryParameters.get(name);
        if (paramList == null) {
            paramList = new LinkedList<String>();
            customQueryParameters.put(name, paramList);
        }
        paramList.add(value);
    }

    /**
     * Copies the internal state of this base class to that of the target request.
     *
     * @return the target request
     */
    protected final <T extends ProcyonRequest> T copyBaseTo(T target) {
        if (customRequestHeaders != null) {
            for (Map.Entry<String, String> e : customRequestHeaders.entrySet())
                target.putCustomRequestHeader(e.getKey(), e.getValue());
        }
        if (customQueryParameters != null) {
            for (Map.Entry<String, List<String>> e : customQueryParameters.entrySet()) {
                if (e.getValue() != null) {
                    for (String value : e.getValue()) {
                        target.putCustomQueryParameter(e.getKey(), value);
                    }
                }
            }
        }

        target.setRequestCredentialsProvider(credentialsProvider);
        return target;
    }

    /**
     * Returns the source object from which the current object was cloned; or null if there isn't
     * one.
     */
    public ProcyonRequest getCloneSource() {
        return cloneSource;
    }

    /**
     * Returns the root object from which the current object was cloned; or null if there isn't one.
     */
    public ProcyonRequest getCloneRoot() {
        ProcyonRequest cloneRoot = cloneSource;
        if (cloneRoot != null) {
            while (cloneRoot.getCloneSource() != null) {
                cloneRoot = cloneRoot.getCloneSource();
            }
        }
        return cloneRoot;
    }

    private void setCloneSource(ProcyonRequest cloneSource) {
        this.cloneSource = cloneSource;
    }

    /**
     * Returns the amount of time to wait (in milliseconds) for the request to complete before
     * giving up and timing out. A non-positive value disables this feature.
     * <p>
     * This feature requires buffering the entire response (for non-streaming APIs) into memory to
     * enforce a hard timeout when reading the response. For APIs that return large responses this
     * could be expensive.
     * <p>
     * <p>
     * The request timeout feature doesn't have strict guarantees on how quickly a request is
     * aborted when the timeout is breached. The typical case aborts the request within a few
     * milliseconds but there may occasionally be requests that don't get aborted until several
     * seconds after the timer has been breached. Because of this the request timeout feature should
     * not be used when absolute precision is needed.
     * </p>
     * <p>
     * <b>Note:</b> This feature is not compatible with Java 1.6.
     * </p>
     *
     * @return The amount of time to wait (in milliseconds) for the request to complete before
     *         giving up and timing out. A non-positive value disables the timeout for this request.
     */
    public Integer getSdkRequestTimeout() {
        return sdkRequestTimeout;
    }

    /**
     * Sets the amount of time to wait (in milliseconds) for the request to complete before giving
     * up and timing out. A non-positive value disables this feature.
     * <p>
     * This feature requires buffering the entire response (for non-streaming APIs) into memory to
     * enforce a hard timeout when reading the response. For APIs that return large responses this
     * could be expensive.
     * <p>
     * <p>
     * The request timeout feature doesn't have strict guarantees on how quickly a request is
     * aborted when the timeout is breached. The typical case aborts the request within a few
     * milliseconds but there may occasionally be requests that don't get aborted until several
     * seconds after the timer has been breached. Because of this the request timeout feature should
     * not be used when absolute precision is needed.
     * </p>
     * <p>
     * <b>Note:</b> This feature is not compatible with Java 1.6.
     * </p>
     *
     * @param sdkRequestTimeout
     *            The amount of time to wait (in milliseconds) for the request to complete before
     *            giving up and timing out. A non-positive value disables the timeout for this
     *            request.
     */
    public void setSdkRequestTimeout(int sdkRequestTimeout) {
        this.sdkRequestTimeout = sdkRequestTimeout;
    }

    /**
     * Sets the amount of time to wait (in milliseconds) for the request to complete before giving
     * up and timing out. A non-positive value disables this feature. Returns the updated
     * AmazonWebServiceRequest object so that additional method calls may be chained together.
     * <p>
     * This feature requires buffering the entire response (for non-streaming APIs) into memory to
     * enforce a hard timeout when reading the response. For APIs that return large responses this
     * could be expensive.
     * <p>
     * <p>
     * The request timeout feature doesn't have strict guarantees on how quickly a request is
     * aborted when the timeout is breached. The typical case aborts the request within a few
     * milliseconds but there may occasionally be requests that don't get aborted until several
     * seconds after the timer has been breached. Because of this the request timeout feature should
     * not be used when absolute precision is needed.
     * </p>
     * <p>
     * <b>Note:</b> This feature is not compatible with Java 1.6.
     * </p>
     *
     * @param sdkRequestTimeout
     *            The amount of time to wait (in milliseconds) for the request to complete before
     *            giving up and timing out. A non-positive value disables the timeout for this
     *            request.
     * @return The updated {@link ProcyonRequest} object.
     */
    public <T extends ProcyonRequest> T withSdkRequestTimeout(int sdkRequestTimeout) {
        setSdkRequestTimeout(sdkRequestTimeout);
        @SuppressWarnings("unchecked")
        T t = (T) this;
        return t;
    }

    /**
     * Returns the amount of time (in milliseconds) to allow the client to complete the execution of
     * an API call. This timeout covers the entire client execution except for marshalling. This
     * includes request handler execution, all HTTP request including retries, unmarshalling, etc.
     * <p>
     * This feature requires buffering the entire response (for non-streaming APIs) into memory to
     * enforce a hard timeout when reading the response. For APIs that return large responses this
     * could be expensive.
     * <p>
     * <p>
     * The client execution timeout feature doesn't have strict guarantees on how quickly a request
     * is aborted when the timeout is breached. The typical case aborts the request within a few
     * milliseconds but there may occasionally be requests that don't get aborted until several
     * seconds after the timer has been breached. Because of this the client execution timeout
     * feature should not be used when absolute precision is needed.
     * </p>
     * <p>
     * This may be used together with {@link ProcyonRequest#setSdkRequestTimeout(int)} to
     * enforce both a timeout on each individual HTTP request (i.e. each retry) and the total time
     * spent on all requests across retries (i.e. the 'client execution' time). A non-positive value
     * disables this feature.
     * </p>
     * <p>
     * <b>Note:</b> This feature is not compatible with Java 1.6.
     * </p>
     *
     * @return The amount of time (in milliseconds) to allow the client to complete the execution of
     *         an API call. A non-positive value disables the timeout for this request.
     */
    public Integer getSdkClientExecutionTimeout() {
        return this.sdkClientExecutionTimeout;
    }

    /**
     * Sets the amount of time (in milliseconds) to allow the client to complete the execution of
     * an API call. This timeout covers the entire client execution except for marshalling. This
     * includes request handler execution, all HTTP request including retries, unmarshalling, etc.
     * <p>
     * This feature requires buffering the entire response (for non-streaming APIs) into memory to
     * enforce a hard timeout when reading the response. For APIs that return large responses this
     * could be expensive.
     * <p>
     * <p>
     * The client execution timeout feature doesn't have strict guarantees on how quickly a request
     * is aborted when the timeout is breached. The typical case aborts the request within a few
     * milliseconds but there may occasionally be requests that don't get aborted until several
     * seconds after the timer has been breached. Because of this the client execution timeout
     * feature should not be used when absolute precision is needed.
     * </p>
     * <p>
     * This may be used together with {@link ProcyonRequest#setSdkRequestTimeout(int)} to
     * enforce both a timeout on each individual HTTP request (i.e. each retry) and the total time
     * spent on all requests across retries (i.e. the 'client execution' time). A non-positive value
     * disables this feature.
     * </p>
     * <p>
     * <b>Note:</b> This feature is not compatible with Java 1.6.
     * </p>
     *
     * @param sdkClientExecutionTimeout
     *            The amount of time (in milliseconds) to allow the client to complete the execution
     *            of an API call. A non-positive value disables the timeout for this request.
     */
    public void setSdkClientExecutionTimeout(int sdkClientExecutionTimeout) {
        this.sdkClientExecutionTimeout = sdkClientExecutionTimeout;
    }

    /**
     * Sets the amount of time (in milliseconds) to allow the client to complete the execution of
     * an API call. This timeout covers the entire client execution except for marshalling. This
     * includes request handler execution, all HTTP request including retries, unmarshalling, etc.
     * <p>
     * This feature requires buffering the entire response (for non-streaming APIs) into memory to
     * enforce a hard timeout when reading the response. For APIs that return large responses this
     * could be expensive.
     * <p>
     * <p>
     * The client execution timeout feature doesn't have strict guarantees on how quickly a request
     * is aborted when the timeout is breached. The typical case aborts the request within a few
     * milliseconds but there may occasionally be requests that don't get aborted until several
     * seconds after the timer has been breached. Because of this the client execution timeout
     * feature should not be used when absolute precision is needed.
     * </p>
     * <p>
     * This may be used together with {@link ProcyonRequest#setSdkRequestTimeout(int)} to
     * enforce both a timeout on each individual HTTP request (i.e. each retry) and the total time
     * spent on all requests across retries (i.e. the 'client execution' time). A non-positive value
     * disables this feature.
     * </p>
     * <p>
     * <b>Note:</b> This feature is not compatible with Java 1.6.
     * </p>
     *
     * @param sdkClientExecutionTimeout
     *            The amount of time (in milliseconds) to allow the client to complete the execution
     *            of an API call. A non-positive value disables the timeout for this request.
     * @return The updated AmazonWebServiceRequest object for method chaining
     * @see {@link ProcyonRequest#setSdkRequestTimeout(int)} to enforce a timeout per HTTP
     *      request
     */
    public <T extends ProcyonRequest> T withSdkClientExecutionTimeout(int sdkClientExecutionTimeout) {
        setSdkClientExecutionTimeout(sdkClientExecutionTimeout);
        @SuppressWarnings("unchecked")
        T t = (T) this;
        return t;
    }

    /**
     * Creates a shallow clone of this object for all fields except the handler context. Explicitly does <em>not</em> clone the
     * deep structure of the other fields in the message.
     *
     * @see Object#clone()
     */
    @Override
    public ProcyonRequest clone() {
        try {
            ProcyonRequest cloned = (ProcyonRequest) super.clone();
            cloned.setCloneSource(this);

            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(
                    "Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }
}
