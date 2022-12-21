package io.jenkins.plugins.credentials.secretsmanager.config;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import io.jenkins.plugins.credentials.secretsmanager.Messages;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import javax.annotation.Nonnull;
import java.io.Serializable;

@Symbol("filter")
public class Filter extends AbstractDescribableImpl<Filter> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;

    private String value;

    @DataBoundConstructor
    public Filter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    @DataBoundSetter
    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    @DataBoundSetter
    public void setValue(String value) {
        this.value = value;
    }

    @Extension
    @Symbol("filter")
    @SuppressWarnings("unused")
    public static class DescriptorImpl extends Descriptor<Filter> {

        @Override
        @Nonnull
        public String getDisplayName() {
            return Messages.filter();
        }
    }
}
