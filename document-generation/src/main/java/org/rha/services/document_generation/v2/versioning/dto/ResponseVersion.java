package org.rha.services.document_generation.v2.versioning.dto;

import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class ResponseVersion {
    private Long versionId;
    private String sourceSystemId;
    private String documentType;
    private String documentUrn;
    private Map<String, URI> templatedDocuments;

    public ResponseVersion(Long versionId, String sourceSystemId, String documentType,
                           String documentUrn, Map<String, URI> templatedDocuments) {
        this.versionId = versionId;
        this.sourceSystemId = sourceSystemId;
        this.documentType = documentType;
        this.documentUrn = documentUrn;
        this.templatedDocuments = templatedDocuments;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(final Long versionId) {
        this.versionId = versionId;
    }

    public String getSourceSystemId() {
        return sourceSystemId;
    }

    public void setSourceSystemId(final String sourceSystemId) {
        this.sourceSystemId = sourceSystemId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(final String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentUrn() {
        return documentUrn;
    }

    public void setDocumentUrn(final String documentUrn) {
        this.documentUrn = documentUrn;
    }

    public Map<String, URI> getTemplatedDocuments() {
        return templatedDocuments;
    }

    public void setTemplatedDocuments(final Map<String, URI> templatedDocuments) {
        this.templatedDocuments = Objects.requireNonNullElse(templatedDocuments, Collections.emptyMap());
    }
}
