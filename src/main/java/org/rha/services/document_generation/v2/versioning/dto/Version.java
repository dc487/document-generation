package org.rha.services.document_generation.v2.versioning.dto;

import com.ctc.wstx.shaded.msv_core.util.Uri;

public class Version {
    private Long versionId;
    private String sourceSystemId;
    private String documentType;
    private String documentUrn;
    private Uri templatedDocumentUri;

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

    public Uri getTemplatedDocumentUri() {
        return templatedDocumentUri;
    }

    public void setTemplatedDocumentUri(final Uri templatedDocumentUri) {
        this.templatedDocumentUri = templatedDocumentUri;
    }
}
