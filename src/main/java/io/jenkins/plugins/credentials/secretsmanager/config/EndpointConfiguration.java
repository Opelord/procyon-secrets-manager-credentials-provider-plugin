package io.jenkins.plugins.credentials.secretsmanager.config;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import io.jenkins.plugins.credentials.secretsmanager.Messages;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Objects;

public class EndpointConfiguration extends AbstractDescribableImpl<EndpointConfiguration>
        implements Serializable {
    private static final long serialVersionUID = 1L;

    private String serviceEndpoint;

    @DataBoundConstructor
    public EndpointConfiguration(String serviceEndpoint) {
        this.serviceEndpoint = serviceEndpoint;
    }

    public String getServiceEndpoint() {
        return serviceEndpoint;
    }

    @DataBoundSetter
    @SuppressWarnings("unused")
    public void setServiceEndpoint(String serviceEndpoint) {
        this.serviceEndpoint = serviceEndpoint;
    }

    @SuppressWarnings("unused")
    @Override
    public String toString() {
        return "Service Endpoint = " + serviceEndpoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndpointConfiguration that = (EndpointConfiguration) o;
        return Objects.equals(serviceEndpoint, that.serviceEndpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceEndpoint);
    }

    public ProcyonClientBuilder.EndpointConfiguration build() {
        if (serviceEndpoint == null) {
            return null;
        }

        return new ProcyonClientBuilder.EndpointConfiguration(serviceEndpoint);
    }

    @Extension
    @Symbol("endpointConfiguration")
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends Descriptor<EndpointConfiguration> {
        @Override
        @Nonnull
        public String getDisplayName() {
            return Messages.endpointConfiguration();
        }
    }
}
