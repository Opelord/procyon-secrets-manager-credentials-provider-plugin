package io.jenkins.plugins.credentials.secretsmanager.model;

/*
 * Copyright 2017-2022 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with
 * the License.
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.SdkClientException;
import com.amazonaws.protocol.*;

public class Filter implements Serializable, Cloneable, StructuredPojo {

    /**
     * <p>
     * The following are keys you can use:
     * </p>
     * <ul>
     * <li>
     * <p>
     * <b>tag-key</b>: Prefix match, case-sensitive.
     * </p>
     * </li>
     * <li>
     * <p>
     * <b>tag-value</b>: Prefix match, case-sensitive.
     * </p>
     * </li>
     * </ul>
     */
    private String key;
    /**
     * <p>
     * The keyword to filter for.
     * </p>
     * <p>
     * You can prefix your search value with an exclamation mark (<code>!</code>) in order to perform negation filters.
     * </p>
     */
    private String value;

    /**
     * <p>
     * The following are keys you can use:
     * </p>
     * <ul>
     * <li>
     * <p>
     * <b>tag-key</b>: Prefix match, case-sensitive.
     * </p>
     * </li>
     * <li>
     * <p>
     * <b>tag-value</b>: Prefix match, case-sensitive.
     * </p>
     * </li>
     * </ul>
     *
     * @param key
     *        The following are keys you can use:</p>
     *        <ul>
     *        <li>
     *        <p>
     *        <b>tag-key</b>: Prefix match, case-sensitive.
     *        </p>
     *        </li>
     *        <li>
     *        <p>
     *        <b>tag-value</b>: Prefix match, case-sensitive.
     *        </p>
     *        </li>
     */

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * <p>
     * The following are keys you can use:
     * </p>
     * <ul>
     * <li>
     * <p>
     * <b>tag-key</b>: Prefix match, case-sensitive.
     * </p>
     * </li>
     * <li>
     * <p>
     * <b>tag-value</b>: Prefix match, case-sensitive.
     * </p>
     * </li>
     * </ul>
     *
     * @return The following are keys you can use:</p>
     *         <ul>
     *         <li>
     *         <p>
     *         <b>tag-key</b>: Prefix match, case-sensitive.
     *         </p>
     *         </li>
     *         <li>
     *         <p>
     *         <b>tag-value</b>: Prefix match, case-sensitive.
     *         </p>
     *         </li>
     */

    public String getKey() {
        return this.key;
    }

    /**
     * <p>
     * The following are keys you can use:
     * </p>
     * <ul>
     * <li>
     * <p>
     * <b>tag-key</b>: Prefix match, case-sensitive.
     * </p>
     * </li>
     * <li>
     * <p>
     * <b>tag-value</b>: Prefix match, case-sensitive.
     * </p>
     * </li>
     * </ul>
     *
     * @param key
     *        The following are keys you can use:</p>
     *        <ul>
     *        <li>
     *        <p>
     *        <b>tag-key</b>: Prefix match, case-sensitive.
     *        </p>
     *        </li>
     *        <li>
     *        <p>
     *        <b>tag-value</b>: Prefix match, case-sensitive.
     *        </p>
     *        </li>
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public Filter withKey(String key) {
        setKey(key);
        return this;
    }

    /**
     * <p>
     * The keyword to filter for.
     * </p>
     *
     * @return The keyword to filter for.</p>
     */

    public String getValue() {
        return value;
    }

    /**
     * <p>
     * The keyword to filter for.
     * </p>
     *
     * @param value
     *        The keyword to filter for.</p>
     */

    public void setValue(String value) {
        if (value == null) {
            this.value = null;
            return;
        }

        this.value = value;
    }

    /**
     * <p>
     * The keyword to filter for.
     * </p>
     * <p>
     * <b>NOTE:</b> This method appends the values to the existing list (if any). Use
     * {@link #setValue(String)} or {@link #withValue(String)} if you want to override the
     * existing values.
     * </p>
     *
     * @param value
     *        The keyword to filter for.</p>
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public Filter withValue(String value) {
        this.value = value;

        return this;
    }

    /**
     * Returns a string representation of this object. This is useful for testing and debugging. Sensitive data will be
     * redacted from this string using a placeholder value.
     *
     * @return A string representation of this object.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getKey() != null)
            sb.append("Key: ").append(getKey()).append(",");
        if (getValue() != null)
            sb.append("Values: ").append(getValue());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (!(obj instanceof Filter))
            return false;
        Filter other = (Filter) obj;
        if (other.getKey() == null ^ this.getKey() == null)
            return false;
        if (other.getKey() != null && !other.getKey().equals(this.getKey()))
            return false;
        if (other.getValue() == null ^ this.getValue() == null)
            return false;
        return other.getValue() == null || other.getValue().equals(this.getValue());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getKey() == null) ? 0 : getKey().hashCode());
        hashCode = prime * hashCode + ((getValue() == null) ? 0 : getValue().hashCode());
        return hashCode;
    }

    @Override
    public Filter clone() {
        try {
            return (Filter) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        FilterMarshaller.getInstance().marshall(this, protocolMarshaller);
    }

    public static class FilterMarshaller {

        private static final MarshallingInfo<String> KEY_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
                .marshallLocationName("Key").build();
        private static final MarshallingInfo<String> VALUE_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
                .marshallLocationName("Value").build();

        private static final FilterMarshaller instance = new FilterMarshaller();

        public static FilterMarshaller getInstance() {
            return instance;
        }

        /**
         * Marshall the given parameter object.
         */
        public void marshall(Filter filter, ProtocolMarshaller protocolMarshaller) {

            if (filter == null) {
                throw new SdkClientException("Invalid argument passed to marshall(...)");
            }

            try {
                protocolMarshaller.marshall(filter.getKey(), KEY_BINDING);
                protocolMarshaller.marshall(filter.getValue(), VALUE_BINDING);
            } catch (Exception e) {
                throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
            }
        }
    }
}