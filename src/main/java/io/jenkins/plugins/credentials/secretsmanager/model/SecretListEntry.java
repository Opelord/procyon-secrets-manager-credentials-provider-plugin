package io.jenkins.plugins.credentials.secretsmanager.model;

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

import com.amazonaws.SdkClientException;
import com.amazonaws.protocol.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class SecretListEntry implements Serializable, Cloneable, StructuredPojo {

    /**
     * <p>
     * Identifier for secret.
     * </p>
     */
    private Integer ID;
    /**
     * <p>
     * The friendly name of the secret. You can use forward slashes in the name to represent a path hierarchy. For
     * example, <code>/prod/databases/dbserver1</code> could represent the secret for a server named
     * <code>dbserver1</code> in the folder <code>databases</code> in the folder <code>prod</code>.
     * </p>
     */
    private String name;
    /**
     * <p>
     * The user-provided description of the secret.
     * </p>
     */
    private String description;
    /**
     * <p>
     * Indicates whether automatic, scheduled rotation is enabled for this secret.
     * </p>
     */
    private Boolean rotationEnabled;
    /**
     * <p>
     * The most recent date and time that the Secrets Manager rotation process was successfully completed. This value is
     * null if the secret hasn't ever rotated.
     * </p>
     */
    private java.util.Date lastRotatedDate;
    /**
     * <p>
     * The last date and time that this secret was modified in any way.
     * </p>
     */
    private java.util.Date lastChangedDate;
    /**
     * <p>
     * The date that the secret was last accessed in the Region. This field is omitted if the secret has never been
     * retrieved in the Region.
     * </p>
     */
    private java.util.Date lastAccessedDate;
    /**
     * <p>
     * The date and time the deletion of the secret occurred. Not present on active secrets. The secret can be recovered
     * until the number of days in the recovery window has passed, as specified in the <code>RecoveryWindowInDays</code>
     * parameter of the <a href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_DeleteSecret.html">
     * <code>DeleteSecret</code> </a> operation.
     * </p>
     */
    private java.util.Date deletedDate;
    /**
     * <p>
     * The list of user-defined tags associated with the secret. To add tags to a secret, use <a
     * href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_TagResource.html">
     * <code>TagResource</code> </a>. To remove tags, use <a
     * href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_UntagResource.html">
     * <code>UntagResource</code> </a>.
     * </p>
     */
    private Map<String,String> tags;
    /**
     * <p>
     * A list of all of the currently assigned <code>SecretVersionStage</code> staging labels and the
     * <code>SecretVersionId</code> attached to each one. Staging labels are used to keep track of the different
     * versions during the rotation process.
     * </p>
     * <note>
     * <p>
     * A version that does not have any <code>SecretVersionStage</code> is considered deprecated and subject to
     * deletion. Such versions are not included in this list.
     * </p>
     * </note>
     */
    private java.util.Map<String, java.util.List<String>> secretVersionsToStages;
    /**
     * <p>
     * Returns the name of the service that created the secret.
     * </p>
     */
    private String owningService;
    /**
     * <p>
     * The date and time when a secret was created.
     * </p>
     */
    private java.util.Date createdDate;

    public void setID(Integer id) {
        this.ID = id;
    }

    public Integer getID() {
        return this.ID;
    }

    /**
    <p>
    Returns a reference to this object so that method calls can be chained together.
    </p>
    */
    public SecretListEntry withID(Integer id) {
        setID(id);
        return this;
    }

    /**
     * <p>
     * The friendly name of the secret. You can use forward slashes in the name to represent a path hierarchy. For
     * example, <code>/prod/databases/dbserver1</code> could represent the secret for a server named
     * <code>dbserver1</code> in the folder <code>databases</code> in the folder <code>prod</code>.
     * </p>
     *
     * @param name
     *        The friendly name of the secret. You can use forward slashes in the name to represent a path hierarchy.
     *        For example, <code>/prod/databases/dbserver1</code> could represent the secret for a server named
     *        <code>dbserver1</code> in the folder <code>databases</code> in the folder <code>prod</code>.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>
     * The friendly name of the secret. You can use forward slashes in the name to represent a path hierarchy. For
     * example, <code>/prod/databases/dbserver1</code> could represent the secret for a server named
     * <code>dbserver1</code> in the folder <code>databases</code> in the folder <code>prod</code>.
     * </p>
     *
     * @return The friendly name of the secret. You can use forward slashes in the name to represent a path hierarchy.
     *         For example, <code>/prod/databases/dbserver1</code> could represent the secret for a server named
     *         <code>dbserver1</code> in the folder <code>databases</code> in the folder <code>prod</code>.
     */

    public String getName() {
        return this.name;
    }

    /**
     * <p>
     * The friendly name of the secret. You can use forward slashes in the name to represent a path hierarchy. For
     * example, <code>/prod/databases/dbserver1</code> could represent the secret for a server named
     * <code>dbserver1</code> in the folder <code>databases</code> in the folder <code>prod</code>.
     * </p>
     *
     * @param name
     *        The friendly name of the secret. You can use forward slashes in the name to represent a path hierarchy.
     *        For example, <code>/prod/databases/dbserver1</code> could represent the secret for a server named
     *        <code>dbserver1</code> in the folder <code>databases</code> in the folder <code>prod</code>.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public SecretListEntry withName(String name) {
        setName(name);
        return this;
    }

    /**
     * <p>
     * The user-provided description of the secret.
     * </p>
     *
     * @param description
     *        The user-provided description of the secret.
     */

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * <p>
     * The user-provided description of the secret.
     * </p>
     *
     * @return The user-provided description of the secret.
     */

    public String getDescription() {
        return this.description;
    }

    /**
     * <p>
     * The user-provided description of the secret.
     * </p>
     *
     * @param description
     *        The user-provided description of the secret.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public SecretListEntry withDescription(String description) {
        setDescription(description);
        return this;
    }

    /**
     * <p>
     * Indicates whether automatic, scheduled rotation is enabled for this secret.
     * </p>
     *
     * @param rotationEnabled
     *        Indicates whether automatic, scheduled rotation is enabled for this secret.
     */

    public void setRotationEnabled(Boolean rotationEnabled) {
        this.rotationEnabled = rotationEnabled;
    }

    /**
     * <p>
     * Indicates whether automatic, scheduled rotation is enabled for this secret.
     * </p>
     *
     * @return Indicates whether automatic, scheduled rotation is enabled for this secret.
     */

    public Boolean getRotationEnabled() {
        return this.rotationEnabled;
    }

    /**
     * <p>
     * Indicates whether automatic, scheduled rotation is enabled for this secret.
     * </p>
     *
     * @param rotationEnabled
     *        Indicates whether automatic, scheduled rotation is enabled for this secret.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public SecretListEntry withRotationEnabled(Boolean rotationEnabled) {
        setRotationEnabled(rotationEnabled);
        return this;
    }

    /**
     * <p>
     * Indicates whether automatic, scheduled rotation is enabled for this secret.
     * </p>
     *
     * @return Indicates whether automatic, scheduled rotation is enabled for this secret.
     */

    public Boolean isRotationEnabled() {
        return this.rotationEnabled;
    }

    /**
     * <p>
     * The most recent date and time that the Secrets Manager rotation process was successfully completed. This value is
     * null if the secret hasn't ever rotated.
     * </p>
     *
     * @param lastRotatedDate
     *        The most recent date and time that the Secrets Manager rotation process was successfully completed. This
     *        value is null if the secret hasn't ever rotated.
     */

    public void setLastRotatedDate(java.util.Date lastRotatedDate) {
        this.lastRotatedDate = new Date(lastRotatedDate.getTime());
    }

    /**
     * <p>
     * The most recent date and time that the Secrets Manager rotation process was successfully completed. This value is
     * null if the secret hasn't ever rotated.
     * </p>
     *
     * @return The most recent date and time that the Secrets Manager rotation process was successfully completed. This
     *         value is null if the secret hasn't ever rotated.
     */

    public java.util.Date getLastRotatedDate() {
        if (this.lastRotatedDate == null) {
            return null;
        }
        return new Date(this.lastRotatedDate.getTime());
    }

    /**
     * <p>
     * The most recent date and time that the Secrets Manager rotation process was successfully completed. This value is
     * null if the secret hasn't ever rotated.
     * </p>
     *
     * @param lastRotatedDate
     *        The most recent date and time that the Secrets Manager rotation process was successfully completed. This
     *        value is null if the secret hasn't ever rotated.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public SecretListEntry withLastRotatedDate(java.util.Date lastRotatedDate) {
        setLastRotatedDate(lastRotatedDate);
        return this;
    }

    /**
     * <p>
     * The last date and time that this secret was modified in any way.
     * </p>
     *
     * @param lastChangedDate
     *        The last date and time that this secret was modified in any way.
     */

    public void setLastChangedDate(java.util.Date lastChangedDate) {
        this.lastChangedDate = new Date(lastChangedDate.getTime());
    }

    /**
     * <p>
     * The last date and time that this secret was modified in any way.
     * </p>
     *
     * @return The last date and time that this secret was modified in any way.
     */

    public java.util.Date getLastChangedDate() {
        if (this.lastChangedDate == null) {
            return null;
        }
        return new Date(this.lastChangedDate.getTime());
    }

    /**
     * <p>
     * The last date and time that this secret was modified in any way.
     * </p>
     *
     * @param lastChangedDate
     *        The last date and time that this secret was modified in any way.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public SecretListEntry withLastChangedDate(java.util.Date lastChangedDate) {
        setLastChangedDate(lastChangedDate);
        return this;
    }

    /**
     * <p>
     * The date that the secret was last accessed in the Region. This field is omitted if the secret has never been
     * retrieved in the Region.
     * </p>
     *
     * @param lastAccessedDate
     *        The date that the secret was last accessed in the Region. This field is omitted if the secret has never
     *        been retrieved in the Region.
     */

    public void setLastAccessedDate(java.util.Date lastAccessedDate) {
        this.lastAccessedDate = new Date(lastAccessedDate.getTime());
    }

    /**
     * <p>
     * The date that the secret was last accessed in the Region. This field is omitted if the secret has never been
     * retrieved in the Region.
     * </p>
     *
     * @return The date that the secret was last accessed in the Region. This field is omitted if the secret has never
     *         been retrieved in the Region.
     */

    public java.util.Date getLastAccessedDate() {
        if (this.lastAccessedDate == null) {
            return null;
        }
        return new Date(this.lastAccessedDate.getTime());
    }

    /**
     * <p>
     * The date that the secret was last accessed in the Region. This field is omitted if the secret has never been
     * retrieved in the Region.
     * </p>
     *
     * @param lastAccessedDate
     *        The date that the secret was last accessed in the Region. This field is omitted if the secret has never
     *        been retrieved in the Region.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public SecretListEntry withLastAccessedDate(java.util.Date lastAccessedDate) {
        setLastAccessedDate(lastAccessedDate);
        return this;
    }

    /**
     * <p>
     * The date and time the deletion of the secret occurred. Not present on active secrets. The secret can be recovered
     * until the number of days in the recovery window has passed, as specified in the <code>RecoveryWindowInDays</code>
     * parameter of the <a href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_DeleteSecret.html">
     * <code>DeleteSecret</code> </a> operation.
     * </p>
     *
     * @param deletedDate
     *        The date and time the deletion of the secret occurred. Not present on active secrets. The secret can be
     *        recovered until the number of days in the recovery window has passed, as specified in the
     *        <code>RecoveryWindowInDays</code> parameter of the <a
     *        href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_DeleteSecret.html">
     *        <code>DeleteSecret</code> </a> operation.
     */

    public void setDeletedDate(java.util.Date deletedDate) {
        this.deletedDate = new Date(deletedDate.getTime());
    }

    /**
     * <p>
     * The date and time the deletion of the secret occurred. Not present on active secrets. The secret can be recovered
     * until the number of days in the recovery window has passed, as specified in the <code>RecoveryWindowInDays</code>
     * parameter of the <a href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_DeleteSecret.html">
     * <code>DeleteSecret</code> </a> operation.
     * </p>
     *
     * @return The date and time the deletion of the secret occurred. Not present on active secrets. The secret can be
     *         recovered until the number of days in the recovery window has passed, as specified in the
     *         <code>RecoveryWindowInDays</code> parameter of the <a
     *         href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_DeleteSecret.html">
     *         <code>DeleteSecret</code> </a> operation.
     */

    public java.util.Date getDeletedDate() {
        if (deletedDate == null) {
            return null;
        }
        return new Date(this.deletedDate.getTime());
    }

    /**
     * <p>
     * The date and time the deletion of the secret occurred. Not present on active secrets. The secret can be recovered
     * until the number of days in the recovery window has passed, as specified in the <code>RecoveryWindowInDays</code>
     * parameter of the <a href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_DeleteSecret.html">
     * <code>DeleteSecret</code> </a> operation.
     * </p>
     *
     * @param deletedDate
     *        The date and time the deletion of the secret occurred. Not present on active secrets. The secret can be
     *        recovered until the number of days in the recovery window has passed, as specified in the
     *        <code>RecoveryWindowInDays</code> parameter of the <a
     *        href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_DeleteSecret.html">
     *        <code>DeleteSecret</code> </a> operation.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public SecretListEntry withDeletedDate(java.util.Date deletedDate) {
        setDeletedDate(deletedDate);
        return this;
    }

    /**
     * <p>
     * The list of user-defined tags associated with the secret. To add tags to a secret, use <a
     * href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_TagResource.html">
     * <code>TagResource</code> </a>. To remove tags, use <a
     * href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_UntagResource.html">
     * <code>UntagResource</code> </a>.
     * </p>
     *
     * @return The list of user-defined tags associated with the secret. To add tags to a secret, use <a
     *         href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_TagResource.html">
     *         <code>TagResource</code> </a>. To remove tags, use <a
     *         href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_UntagResource.html">
     *         <code>UntagResource</code> </a>.
     */

    public Map<String, String> getTags() {
        return tags;
    }

    /**
     * <p>
     * The list of user-defined tags associated with the secret. To add tags to a secret, use <a
     * href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_TagResource.html">
     * <code>TagResource</code> </a>. To remove tags, use <a
     * href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_UntagResource.html">
     * <code>UntagResource</code> </a>.
     * </p>
     *
     * @param tags
     *        The list of user-defined tags associated with the secret. To add tags to a secret, use <a
     *        href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_TagResource.html">
     *        <code>TagResource</code> </a>. To remove tags, use <a
     *        href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_UntagResource.html">
     *        <code>UntagResource</code> </a>.
     */

    public void setTags(Map<String,String> tags) {
        this.tags = tags;
    }

    /**
     * <p>
     * The list of user-defined tags associated with the secret. To add tags to a secret, use <a
     * href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_TagResource.html">
     * <code>TagResource</code> </a>. To remove tags, use <a
     * href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_UntagResource.html">
     * <code>UntagResource</code> </a>.
     * </p>
     *
     * @param tags
     *        The list of user-defined tags associated with the secret. To add tags to a secret, use <a
     *        href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_TagResource.html">
     *        <code>TagResource</code> </a>. To remove tags, use <a
     *        href="https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_UntagResource.html">
     *        <code>UntagResource</code> </a>.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public SecretListEntry withTags(Map<String,String> tags) {
        setTags(tags);
        return this;
    }

    /**
     * <p>
     * A list of all of the currently assigned <code>SecretVersionStage</code> staging labels and the
     * <code>SecretVersionId</code> attached to each one. Staging labels are used to keep track of the different
     * versions during the rotation process.
     * </p>
     * <note>
     * <p>
     * A version that does not have any <code>SecretVersionStage</code> is considered deprecated and subject to
     * deletion. Such versions are not included in this list.
     * </p>
     * </note>
     *
     * @return A list of all of the currently assigned <code>SecretVersionStage</code> staging labels and the
     *         <code>SecretVersionId</code> attached to each one. Staging labels are used to keep track of the different
     *         versions during the rotation process.</p> <note>
     *         <p>
     *         A version that does not have any <code>SecretVersionStage</code> is considered deprecated and subject to
     *         deletion. Such versions are not included in this list.
     *         </p>
     */

    public java.util.Map<String, java.util.List<String>> getSecretVersionsToStages() {
        return secretVersionsToStages;
    }

    /**
     * <p>
     * A list of all of the currently assigned <code>SecretVersionStage</code> staging labels and the
     * <code>SecretVersionId</code> attached to each one. Staging labels are used to keep track of the different
     * versions during the rotation process.
     * </p>
     * <note>
     * <p>
     * A version that does not have any <code>SecretVersionStage</code> is considered deprecated and subject to
     * deletion. Such versions are not included in this list.
     * </p>
     * </note>
     *
     * @param secretVersionsToStages
     *        A list of all of the currently assigned <code>SecretVersionStage</code> staging labels and the
     *        <code>SecretVersionId</code> attached to each one. Staging labels are used to keep track of the different
     *        versions during the rotation process.</p> <note>
     *        <p>
     *        A version that does not have any <code>SecretVersionStage</code> is considered deprecated and subject to
     *        deletion. Such versions are not included in this list.
     *        </p>
     */

    public void setSecretVersionsToStages(java.util.Map<String, java.util.List<String>> secretVersionsToStages) {
        this.secretVersionsToStages = secretVersionsToStages;
    }

    /**
     * <p>
     * A list of all of the currently assigned <code>SecretVersionStage</code> staging labels and the
     * <code>SecretVersionId</code> attached to each one. Staging labels are used to keep track of the different
     * versions during the rotation process.
     * </p>
     * <note>
     * <p>
     * A version that does not have any <code>SecretVersionStage</code> is considered deprecated and subject to
     * deletion. Such versions are not included in this list.
     * </p>
     * </note>
     *
     * @param secretVersionsToStages
     *        A list of all of the currently assigned <code>SecretVersionStage</code> staging labels and the
     *        <code>SecretVersionId</code> attached to each one. Staging labels are used to keep track of the different
     *        versions during the rotation process.</p> <note>
     *        <p>
     *        A version that does not have any <code>SecretVersionStage</code> is considered deprecated and subject to
     *        deletion. Such versions are not included in this list.
     *        </p>
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public SecretListEntry withSecretVersionsToStages(java.util.Map<String, java.util.List<String>> secretVersionsToStages) {
        setSecretVersionsToStages(secretVersionsToStages);
        return this;
    }

    /**
     * Add a single SecretVersionsToStages entry
     *
     * @see SecretListEntry#withSecretVersionsToStages
     * @returns a reference to this object so that method calls can be chained together.
     */

    public SecretListEntry addSecretVersionsToStagesEntry(String key, java.util.List<String> value) {
        if (null == this.secretVersionsToStages) {
            this.secretVersionsToStages = new java.util.HashMap<String, java.util.List<String>>();
        }
        if (this.secretVersionsToStages.containsKey(key))
            throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
        this.secretVersionsToStages.put(key, value);
        return this;
    }

    /**
     * Removes all the entries added into SecretVersionsToStages.
     *
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public SecretListEntry clearSecretVersionsToStagesEntries() {
        this.secretVersionsToStages = null;
        return this;
    }

    /**
     * <p>
     * Returns the name of the service that created the secret.
     * </p>
     *
     * @param owningService
     *        Returns the name of the service that created the secret.
     */

    public void setOwningService(String owningService) {
        this.owningService = owningService;
    }

    /**
     * <p>
     * Returns the name of the service that created the secret.
     * </p>
     *
     * @return Returns the name of the service that created the secret.
     */

    public String getOwningService() {
        return this.owningService;
    }

    /**
     * <p>
     * Returns the name of the service that created the secret.
     * </p>
     *
     * @param owningService
     *        Returns the name of the service that created the secret.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public SecretListEntry withOwningService(String owningService) {
        setOwningService(owningService);
        return this;
    }

    /**
     * <p>
     * The date and time when a secret was created.
     * </p>
     *
     * @param createdDate
     *        The date and time when a secret was created.
     */

    public void setCreatedDate(java.util.Date createdDate) {
        this.createdDate = new Date(createdDate.getTime());
    }

    /**
     * <p>
     * The date and time when a secret was created.
     * </p>
     *
     * @return The date and time when a secret was created.
     */

    public java.util.Date getCreatedDate() {
        if (this.createdDate == null) {
            return null;
        }
        return new Date(this.createdDate.getTime());
    }

    /**
     * <p>
     * The date and time when a secret was created.
     * </p>
     *
     * @param createdDate
     *        The date and time when a secret was created.
     * @return Returns a reference to this object so that method calls can be chained together.
     */

    public SecretListEntry withCreatedDate(java.util.Date createdDate) {
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
            sb.append("ID: ").append(getID()).append(",");
        if (getName() != null)
            sb.append("Name: ").append(getName()).append(",");
        if (getDescription() != null)
            sb.append("Description: ").append(getDescription()).append(",");
        if (getRotationEnabled() != null)
            sb.append("RotationEnabled: ").append(getRotationEnabled()).append(",");
        if (getLastRotatedDate() != null)
            sb.append("LastRotatedDate: ").append(getLastRotatedDate()).append(",");
        if (getLastChangedDate() != null)
            sb.append("LastChangedDate: ").append(getLastChangedDate()).append(",");
        if (getLastAccessedDate() != null)
            sb.append("LastAccessedDate: ").append(getLastAccessedDate()).append(",");
        if (getDeletedDate() != null)
            sb.append("DeletedDate: ").append(getDeletedDate()).append(",");
        if (getTags() != null)
            sb.append("Tags: ").append(getTags()).append(",");
        if (getSecretVersionsToStages() != null)
            sb.append("SecretVersionsToStages: ").append(getSecretVersionsToStages()).append(",");
        if (getOwningService() != null)
            sb.append("OwningService: ").append(getOwningService()).append(",");
        if (getCreatedDate() != null)
            sb.append("CreatedDate: ").append(getCreatedDate()).append(",");
        sb.append("}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (obj instanceof SecretListEntry == false)
            return false;
        SecretListEntry other = (SecretListEntry) obj;
        if (other.getID() == null ^ this.getID() == null)
            return false;
        if (other.getID() != null && other.getID().equals(this.getID()) == false)
            return false;
        if (other.getName() == null ^ this.getName() == null)
            return false;
        if (other.getName() != null && other.getName().equals(this.getName()) == false)
            return false;
        if (other.getDescription() == null ^ this.getDescription() == null)
            return false;
        if (other.getDescription() != null && other.getDescription().equals(this.getDescription()) == false)
            return false;
        if (other.getRotationEnabled() == null ^ this.getRotationEnabled() == null)
            return false;
        if (other.getRotationEnabled() != null && other.getRotationEnabled().equals(this.getRotationEnabled()) == false)
            return false;
        if (other.getLastRotatedDate() == null ^ this.getLastRotatedDate() == null)
            return false;
        if (other.getLastRotatedDate() != null && other.getLastRotatedDate().equals(this.getLastRotatedDate()) == false)
            return false;
        if (other.getLastChangedDate() == null ^ this.getLastChangedDate() == null)
            return false;
        if (other.getLastChangedDate() != null && other.getLastChangedDate().equals(this.getLastChangedDate()) == false)
            return false;
        if (other.getLastAccessedDate() == null ^ this.getLastAccessedDate() == null)
            return false;
        if (other.getLastAccessedDate() != null && other.getLastAccessedDate().equals(this.getLastAccessedDate()) == false)
            return false;
        if (other.getDeletedDate() == null ^ this.getDeletedDate() == null)
            return false;
        if (other.getDeletedDate() != null && other.getDeletedDate().equals(this.getDeletedDate()) == false)
            return false;
        if (other.getTags() == null ^ this.getTags() == null)
            return false;
        if (other.getTags() != null && other.getTags().equals(this.getTags()) == false)
            return false;
        if (other.getSecretVersionsToStages() == null ^ this.getSecretVersionsToStages() == null)
            return false;
        if (other.getSecretVersionsToStages() != null && other.getSecretVersionsToStages().equals(this.getSecretVersionsToStages()) == false)
            return false;
        if (other.getOwningService() == null ^ this.getOwningService() == null)
            return false;
        if (other.getOwningService() != null && other.getOwningService().equals(this.getOwningService()) == false)
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
        hashCode = prime * hashCode + ((getDescription() == null) ? 0 : getDescription().hashCode());
        hashCode = prime * hashCode + ((getRotationEnabled() == null) ? 0 : getRotationEnabled().hashCode());
        hashCode = prime * hashCode + ((getLastRotatedDate() == null) ? 0 : getLastRotatedDate().hashCode());
        hashCode = prime * hashCode + ((getLastChangedDate() == null) ? 0 : getLastChangedDate().hashCode());
        hashCode = prime * hashCode + ((getLastAccessedDate() == null) ? 0 : getLastAccessedDate().hashCode());
        hashCode = prime * hashCode + ((getDeletedDate() == null) ? 0 : getDeletedDate().hashCode());
        hashCode = prime * hashCode + ((getTags() == null) ? 0 : getTags().hashCode());
        hashCode = prime * hashCode + ((getSecretVersionsToStages() == null) ? 0 : getSecretVersionsToStages().hashCode());
        hashCode = prime * hashCode + ((getOwningService() == null) ? 0 : getOwningService().hashCode());
        hashCode = prime * hashCode + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        return hashCode;
    }

    @Override
    public SecretListEntry clone() {
        try {
            return (SecretListEntry) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Got a CloneNotSupportedException from Object.clone() " + "even though we're Cloneable!", e);
        }
    }

    @Override
    public void marshall(ProtocolMarshaller protocolMarshaller) {
        SecretListEntryMarshaller.getInstance().marshall(this, protocolMarshaller);
    }

    private static class SecretListEntryMarshaller {

        private static final MarshallingInfo<Integer> ID_BINDING = MarshallingInfo.builder(MarshallingType.INTEGER).marshallLocation(MarshallLocation.PAYLOAD)
                .marshallLocationName("ID").build();
        private static final MarshallingInfo<String> NAME_BINDING = MarshallingInfo.builder(MarshallingType.STRING).marshallLocation(MarshallLocation.PAYLOAD)
                .marshallLocationName("Name").build();
        private static final MarshallingInfo<String> DESCRIPTION_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
                .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("Description").build();
        private static final MarshallingInfo<Boolean> ROTATIONENABLED_BINDING = MarshallingInfo.builder(MarshallingType.BOOLEAN)
                .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("RotationEnabled").build();
        private static final MarshallingInfo<java.util.Date> LASTROTATEDDATE_BINDING = MarshallingInfo.builder(MarshallingType.DATE)
                .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("LastRotatedDate").timestampFormat("unixTimestamp").build();
        private static final MarshallingInfo<java.util.Date> LASTCHANGEDDATE_BINDING = MarshallingInfo.builder(MarshallingType.DATE)
                .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("LastChangedDate").timestampFormat("unixTimestamp").build();
        private static final MarshallingInfo<java.util.Date> LASTACCESSEDDATE_BINDING = MarshallingInfo.builder(MarshallingType.DATE)
                .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("LastAccessedDate").timestampFormat("unixTimestamp").build();
        private static final MarshallingInfo<java.util.Date> DELETEDDATE_BINDING = MarshallingInfo.builder(MarshallingType.DATE)
                .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("DeletedDate").timestampFormat("unixTimestamp").build();
        private static final MarshallingInfo<Map> TAGS_BINDING = MarshallingInfo.builder(MarshallingType.MAP).marshallLocation(MarshallLocation.PAYLOAD)
                .marshallLocationName("Tags").build();
        private static final MarshallingInfo<Map> SECRETVERSIONSTOSTAGES_BINDING = MarshallingInfo.builder(MarshallingType.MAP)
                .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("SecretVersionsToStages").build();
        private static final MarshallingInfo<String> OWNINGSERVICE_BINDING = MarshallingInfo.builder(MarshallingType.STRING)
                .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("OwningService").build();
        private static final MarshallingInfo<java.util.Date> CREATEDDATE_BINDING = MarshallingInfo.builder(MarshallingType.DATE)
                .marshallLocation(MarshallLocation.PAYLOAD).marshallLocationName("CreatedDate").timestampFormat("unixTimestamp").build();

        private static final SecretListEntryMarshaller instance = new SecretListEntryMarshaller();

        public static SecretListEntryMarshaller getInstance() {
            return instance;
        }

        /**
         * Marshall the given parameter object.
         */
        public void marshall(SecretListEntry secretListEntry, ProtocolMarshaller protocolMarshaller) {

            if (secretListEntry == null) {
                throw new SdkClientException("Invalid argument passed to marshall(...)");
            }

            try {
                protocolMarshaller.marshall(secretListEntry.getID(), ID_BINDING);
                protocolMarshaller.marshall(secretListEntry.getName(), NAME_BINDING);
                protocolMarshaller.marshall(secretListEntry.getDescription(), DESCRIPTION_BINDING);
                protocolMarshaller.marshall(secretListEntry.getRotationEnabled(), ROTATIONENABLED_BINDING);
                protocolMarshaller.marshall(secretListEntry.getLastRotatedDate(), LASTROTATEDDATE_BINDING);
                protocolMarshaller.marshall(secretListEntry.getLastChangedDate(), LASTCHANGEDDATE_BINDING);
                protocolMarshaller.marshall(secretListEntry.getLastAccessedDate(), LASTACCESSEDDATE_BINDING);
                protocolMarshaller.marshall(secretListEntry.getDeletedDate(), DELETEDDATE_BINDING);
                protocolMarshaller.marshall(secretListEntry.getTags(), TAGS_BINDING);
                protocolMarshaller.marshall(secretListEntry.getSecretVersionsToStages(), SECRETVERSIONSTOSTAGES_BINDING);
                protocolMarshaller.marshall(secretListEntry.getOwningService(), OWNINGSERVICE_BINDING);
                protocolMarshaller.marshall(secretListEntry.getCreatedDate(), CREATEDDATE_BINDING);
            } catch (Exception e) {
                throw new SdkClientException("Unable to marshall request to JSON: " + e.getMessage(), e);
            }
        }

    }
}
