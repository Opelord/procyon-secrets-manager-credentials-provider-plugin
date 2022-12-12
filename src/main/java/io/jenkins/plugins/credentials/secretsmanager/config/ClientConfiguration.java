/*
 * Copyright 2010-2022 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package io.jenkins.plugins.credentials.secretsmanager.config;


import com.amazonaws.annotation.NotThreadSafe;
import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@NotThreadSafe
public class ClientConfiguration {
    private static final Log log = LogFactory.getLog(ClientConfiguration.class);

    private URI endpoint;

    public ClientConfiguration() {
    }

    public ClientConfiguration(ClientConfiguration other) {
        this.endpoint = other.endpoint;
    }

    public URI getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }

    public ClientConfiguration withEndpoint(URI endpoint) {
        setEndpoint(endpoint);
        return this;
    }
}
