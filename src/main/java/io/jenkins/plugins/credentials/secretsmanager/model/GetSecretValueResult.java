/*
 * Copyright 2017-2022 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with
 * the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package io.jenkins.plugins.credentials.secretsmanager.model;

import io.jenkins.plugins.credentials.secretsmanager.utils.ProcyonResult;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 *
 * @see <a href="http://docs.aws.amazon.com/goto/WebAPI/secretsmanager-2017-10-17/GetSecretValue" target="_top">AWS API
 *      Documentation</a>
 */
public class GetSecretValueResult extends ProcyonResult implements Serializable, Cloneable {

    /**
     * <p>
     * The ID of the secret.
     * </p>
     */
    private Integer ID;
    /**
     * <p>
     * The friendly name of the secret.
     * </p>
     */
    private String name;
    /**
     * <p>
     * The unique identifier of this version of the secret.
     * </p>
     */
    private String versionId;
    /**
     * <p>
     * The decrypted secret value, if the secret value was originally provided as binary data in the form of a byte
     * array. The response parameter represents the binary data as a <a
     * href="https://tools.ietf.org/html/rfc4648#section-4">base64-encoded</a> string.
     * </p>
     * <p>
     * If the secret was created by using the Secrets Manager console, or if the secret value was originally provided as
     * a string, then this field is omitted. The secret value appears in <code>SecretString</code> instead.
     * </p>
     */
    private byte[] secretBinary;
    /**
     * <p>
     * The decrypted secret value, if the secret value was originally provided as a string or through the Secrets
     * Manager console.
     * </p>
     * <p>
     * If this secret was created by using the console, then Secrets Manager stores the information as a JSON structure
     * of key/value pairs.
     * </p>
     */
    private String secretString;
    /**
     * <p>
     * A list of all of the staging labels currently attached to this version of the secret.
     * </p>
     */
    private java.util.List<String> versionStages;
    /**
     * <p>
     * The date and time that this version of the secret was created. If you don't specify which version in
     * <code>VersionId</code> or <code>VersionStage</code>, then Secrets Manager uses the <code>AWSCURRENT</code>
     * version.
     * </p>
     */
    private java.util.Date createdDate;

    /**
     * <p>
     * The ID of the secret.
     * </p>
     *
     * @param ID
     *        The ID of the secret.
     */

    public void setID(Integer ID) {
        this.ID = ID;
    }

    /**
     * <p>
     * The ID of the secret.
     * </p>
     *
     * @return The ID of the secret.
     */

    public Integer getID() {
        return this.ID;
    }

    /**
     * <p>
     * The ID of the secret.
     * </p>
     *
     * @param ID
     *        The ID of the secret.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetSecretValueResult withID(Integer ID) {
        setID(ID);
        return this;
    }

    /**
     * <p>
     * The friendly name of the secret.
     * </p>
     *
     * @param name
     *        The friendly name of the secret.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * The friendly name of the secret.
     * </p>
     *
     * @return The friendly name of the secret.
     */

    public String getName() {
        return this.name;
    }

    /**
     * <p>
     * The friendly name of the secret.
     * </p>
     *
     * @param name
     *        The friendly name of the secret.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetSecretValueResult withName(String name) {
        setName(name);
        return this;
    }

    /**
     * <p>
     * The unique identifier of this version of the secret.
     * </p>
     *
     * @param versionId
     *        The unique identifier of this version of the secret.
     */

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    /**
     * <p>
     * The unique identifier of this version of the secret.
     * </p>
     *
     * @return The unique identifier of this version of the secret.
     */

    public String getVersionId() {
        return this.versionId;
    }

    /**
     * <p>
     * The unique identifier of this version of the secret.
     * </p>
     *
     * @param versionId
     *        The unique identifier of this version of the secret.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetSecretValueResult withVersionId(String versionId) {
        setVersionId(versionId);
        return this;
    }

    /**
     * <p>
     * The decrypted secret value, if the secret value was originally provided as binary data in the form of a byte
     * array. The response parameter represents the binary data as a <a
     * href="https://tools.ietf.org/html/rfc4648#section-4">base64-encoded</a> string.
     * </p>
     * <p>
     * If the secret was created by using the Secrets Manager console, or if the secret value was originally provided as
     * a string, then this field is omitted. The secret value appears in <code>SecretString</code> instead.
     * </p>
     * <p>
     * The AWS SDK for Java performs a Base64 encoding on this field before sending this request to the AWS service.
     * Users of the SDK should not perform Base64 encoding on this field.
     * </p>
     * <p>
     * Warning: ByteBuffers returned by the SDK are mutable. Changes to the content or position of the byte buffer will
     * be seen by all objects that have a reference to this object. It is recommended to call ByteBuffer.duplicate() or
     * ByteBuffer.asReadOnlyBuffer() before using or reading from the buffer. This behavior will be changed in a future
     * major version of the SDK.
     * </p>
     *
     * @param secretBinary
     *        The decrypted secret value, if the secret value was originally provided as binary data in the form of a
     *        byte array. The response parameter represents the binary data as a <a
     *        href="https://tools.ietf.org/html/rfc4648#section-4">base64-encoded</a> string.</p>
     *        <p>
     *        If the secret was created by using the Secrets Manager console, or if the secret value was originally
     *        provided as a string, then this field is omitted. The secret value appears in <code>SecretString</code>
     *        instead.
     */

    public void setSecretBinary(byte[] secretBinary) {
        this.secretBinary = Arrays.copyOf(secretBinary, secretBinary.length);
    }

    /**
     * <p>
     * The decrypted secret value, if the secret value was originally provided as binary data in the form of a byte
     * array. The response parameter represents the binary data as a <a
     * href="https://tools.ietf.org/html/rfc4648#section-4">base64-encoded</a> string.
     * </p>
     * <p>
     * If the secret was created by using the Secrets Manager console, or if the secret value was originally provided as
     * a string, then this field is omitted. The secret value appears in <code>SecretString</code> instead.
     * </p>
     * <p>
     * {@code ByteBuffer}s are stateful. Calling their {@code get} methods changes their {@code position}. We recommend
     * using {@link java.nio.ByteBuffer#asReadOnlyBuffer()} to create a read-only view of the buffer with an independent
     * {@code position}, and calling {@code get} methods on this rather than directly on the returned {@code ByteBuffer}.
     * Doing so will ensure that anyone else using the {@code ByteBuffer} will not be affected by changes to the
     * {@code position}.
     * </p>
     *
     * @return The decrypted secret value, if the secret value was originally provided as binary data in the form of a
     *         byte array. The response parameter represents the binary data as a <a
     *         href="https://tools.ietf.org/html/rfc4648#section-4">base64-encoded</a> string.</p>
     *         <p>
     *         If the secret was created by using the Secrets Manager console, or if the secret value was originally
     *         provided as a string, then this field is omitted. The secret value appears in <code>SecretString</code>
     *         instead.
     */

    public byte[] getSecretBinary() {
        if (this.secretBinary == null) {
            return null;
        }
        return Arrays.copyOf(this.secretBinary, this.secretBinary.length);
    }

    /**
     * <p>
     * The decrypted secret value, if the secret value was originally provided as binary data in the form of a byte
     * array. The response parameter represents the binary data as a <a
     * href="https://tools.ietf.org/html/rfc4648#section-4">base64-encoded</a> string.
     * </p>
     * <p>
     * If the secret was created by using the Secrets Manager console, or if the secret value was originally provided as
     * a string, then this field is omitted. The secret value appears in <code>SecretString</code> instead.
     * </p>
     * <p>
     * The AWS SDK for Java performs a Base64 encoding on this field before sending this request to the AWS service.
     * Users of the SDK should not perform Base64 encoding on this field.
     * </p>
     * <p>
     * Warning: ByteBuffers returned by the SDK are mutable. Changes to the content or position of the byte buffer will
     * be seen by all objects that have a reference to this object. It is recommended to call ByteBuffer.duplicate() or
     * ByteBuffer.asReadOnlyBuffer() before using or reading from the buffer. This behavior will be changed in a future
     * major version of the SDK.
     * </p>
     *
     * @param secretBinary
     *        The decrypted secret value, if the secret value was originally provided as binary data in the form of a
     *        byte array. The response parameter represents the binary data as a <a
     *        href="https://tools.ietf.org/html/rfc4648#section-4">base64-encoded</a> string.</p>
     *        <p>
     *        If the secret was created by using the Secrets Manager console, or if the secret value was originally
     *        provided as a string, then this field is omitted. The secret value appears in <code>SecretString</code>
     *        instead.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetSecretValueResult withSecretBinary(byte[] secretBinary) {
        setSecretBinary(secretBinary);
        return this;
    }

    /**
     * <p>
     * The decrypted secret value, if the secret value was originally provided as a string or through the Secrets
     * Manager console.
     * </p>
     * <p>
     * If this secret was created by using the console, then Secrets Manager stores the information as a JSON structure
     * of key/value pairs.
     * </p>
     *
     * @param secretString
     *        The decrypted secret value, if the secret value was originally provided as a string or through the Secrets
     *        Manager console.</p>
     *        <p>
     *        If this secret was created by using the console, then Secrets Manager stores the information as a JSON
     *        structure of key/value pairs.
     */

    public void setSecretString(String secretString) {
        this.secretString = secretString;
    }

    /**
     * <p>
     * The decrypted secret value, if the secret value was originally provided as a string or through the Secrets
     * Manager console.
     * </p>
     * <p>
     * If this secret was created by using the console, then Secrets Manager stores the information as a JSON structure
     * of key/value pairs.
     * </p>
     *
     * @return The decrypted secret value, if the secret value was originally provided as a string or through the
     *         Secrets Manager console.</p>
     *         <p>
     *         If this secret was created by using the console, then Secrets Manager stores the information as a JSON
     *         structure of key/value pairs.
     */

    public String getSecretString() {
        return this.secretString;
    }

    /**
     * <p>
     * The decrypted secret value, if the secret value was originally provided as a string or through the Secrets
     * Manager console.
     * </p>
     * <p>
     * If this secret was created by using the console, then Secrets Manager stores the information as a JSON structure
     * of key/value pairs.
     * </p>
     *
     * @param secretString
     *        The decrypted secret value, if the secret value was originally provided as a string or through the Secrets
     *        Manager console.</p>
     *        <p>
     *        If this secret was created by using the console, then Secrets Manager stores the information as a JSON
     *        structure of key/value pairs.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetSecretValueResult withSecretString(String secretString) {
        setSecretString(secretString);
        return this;
    }

    /**
     * <p>
     * A list of all of the staging labels currently attached to this version of the secret.
     * </p>
     *
     * @return A list of all of the staging labels currently attached to this version of the secret.
     */

    public java.util.List<String> getVersionStages() {
        return versionStages;
    }

    /**
     * <p>
     * A list of all of the staging labels currently attached to this version of the secret.
     * </p>
     *
     * @param versionStages
     *        A list of all of the staging labels currently attached to this version of the secret.
     */

    public void setVersionStages(java.util.Collection<String> versionStages) {
        if (versionStages == null) {
            this.versionStages = null;
            return;
        }

        this.versionStages = new java.util.ArrayList<String>(versionStages);
    }

    /**
     * <p>
     * A list of all of the staging labels currently attached to this version of the secret.
     * </p>
     * <p>
     * <b>NOTE:</b> This method appends the values to the existing list (if any). Use
     * {@link #setVersionStages(java.util.Collection)} or {@link #withVersionStages(java.util.Collection)} if you want
     * to override the existing values.
     * </p>
     *
     * @param versionStages
     *        A list of all of the staging labels currently attached to this version of the secret.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetSecretValueResult withVersionStages(String... versionStages) {
        if (this.versionStages == null) {
            setVersionStages(new java.util.ArrayList<String>(versionStages.length));
        }
        for (String ele : versionStages) {
            this.versionStages.add(ele);
        }
        return this;
    }

    /**
     * <p>
     * A list of all of the staging labels currently attached to this version of the secret.
     * </p>
     *
     * @param versionStages
     *        A list of all of the staging labels currently attached to this version of the secret.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetSecretValueResult withVersionStages(java.util.Collection<String> versionStages) {
        setVersionStages(versionStages);
        return this;
    }

    /**
     * <p>
     * The date and time that this version of the secret was created. If you don't specify which version in
     * <code>VersionId</code> or <code>VersionStage</code>, then Secrets Manager uses the <code>AWSCURRENT</code>
     * version.
     * </p>
     *
     * @param createdDate
     *        The date and time that this version of the secret was created. If you don't specify which version in
     *        <code>VersionId</code> or <code>VersionStage</code>, then Secrets Manager uses the <code>AWSCURRENT</code>
     *        version.
     */

    public void setCreatedDate(java.util.Date createdDate) {
        this.createdDate = new Date(createdDate.getTime());
    }

    /**
     * <p>
     * The date and time that this version of the secret was created. If you don't specify which version in
     * <code>VersionId</code> or <code>VersionStage</code>, then Secrets Manager uses the <code>AWSCURRENT</code>
     * version.
     * </p>
     *
     * @return The date and time that this version of the secret was created. If you don't specify which version in
     *         <code>VersionId</code> or <code>VersionStage</code>, then Secrets Manager uses the
     *         <code>AWSCURRENT</code> version.
     */

    public java.util.Date getCreatedDate() {
        if (this.createdDate == null) {
            return null;
        }
        return new java.util.Date(this.createdDate.getTime());
    }

    /**
     * <p>
     * The date and time that this version of the secret was created. If you don't specify which version in
     * <code>VersionId</code> or <code>VersionStage</code>, then Secrets Manager uses the <code>AWSCURRENT</code>
     * version.
     * </p>
     *
     * @param createdDate
     *        The date and time that this version of the secret was created. If you don't specify which version in
     *        <code>VersionId</code> or <code>VersionStage</code>, then Secrets Manager uses the <code>AWSCURRENT</code>
     *        version.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public GetSecretValueResult withCreatedDate(java.util.Date createdDate) {
        setCreatedDate(createdDate);
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
        if (getID() != null)
            sb.append("ARN: ").append(getID()).append(",");
        if (getName() != null)
            sb.append("Name: ").append(getName()).append(",");
        if (getVersionId() != null)
            sb.append("VersionId: ").append(getVersionId()).append(",");
        if (getSecretBinary() != null)
            sb.append("SecretBinary: ").append("***Sensitive Data Redacted***").append(",");
        if (getSecretString() != null)
            sb.append("SecretString: ").append("***Sensitive Data Redacted***").append(",");
        if (getVersionStages() != null)
            sb.append("VersionStages: ").append(getVersionStages()).append(",");
        if (getCreatedDate() != null)
            sb.append("CreatedDate: ").append(getCreatedDate());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof GetSecretValueResult == false)
            return false;
        GetSecretValueResult other = (GetSecretValueResult) obj;
        if (other.getID() == null ^ this.getID() == null)
            return false;
        if (other.getID() != null && other.getID().equals(this.getID()) == false)
            return false;
        if (other.getName() == null ^ this.getName() == null)
            return false;
        if (other.getName() != null && other.getName().equals(this.getName()) == false)
            return false;
        if (other.getVersionId() == null ^ this.getVersionId() == null)
            return false;
        if (other.getVersionId() != null && other.getVersionId().equals(this.getVersionId()) == false)
            return false;
        if (other.getSecretBinary() == null ^ this.getSecretBinary() == null)
            return false;
        if (other.getSecretBinary() != null && Arrays.equals(other.getSecretBinary(), this.getSecretBinary()) == false)
            return false;
        if (other.getSecretString() == null ^ this.getSecretString() == null)
            return false;
        if (other.getSecretString() != null && other.getSecretString().equals(this.getSecretString()) == false)
            return false;
        if (other.getVersionStages() == null ^ this.getVersionStages() == null)
            return false;
        if (other.getVersionStages() != null && other.getVersionStages().equals(this.getVersionStages()) == false)
            return false;
        if (other.getCreatedDate() == null ^ this.getCreatedDate() == null)
            return false;
        if (other.getCreatedDate() != null && other.getCreatedDate().equals(this.getCreatedDate()) == false)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        hashCode = prime * hashCode + ((getID() == null) ? 0 : getID().hashCode());
        hashCode = prime * hashCode + ((getName() == null) ? 0 : getName().hashCode());
        hashCode = prime * hashCode + ((getVersionId() == null) ? 0 : getVersionId().hashCode());
        hashCode = prime * hashCode + ((getSecretBinary() == null) ? 0 : Arrays.hashCode(getSecretBinary()));
        hashCode = prime * hashCode + ((getSecretString() == null) ? 0 : getSecretString().hashCode());
        hashCode = prime * hashCode + ((getVersionStages() == null) ? 0 : getVersionStages().hashCode());
        hashCode = prime * hashCode + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        return hashCode;
    }

    @Override
    public GetSecretValueResult clone() {
        try {
            return (GetSecretValueResult) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

}
