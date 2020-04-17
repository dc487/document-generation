package org.rha.services.document_generation.v2.dto;

import com.ctc.wstx.shaded.msv_core.util.Uri;

public class CreateVersionRequest {
    private String sourceSystemId;
    private String documentType;
    private String documentUrn;
    private Uri documentContentUri;

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

    public Uri getDocumentContentUri() {
        return documentContentUri;
    }

    public void setDocumentContentUri(final Uri documentContentUri) {
        this.documentContentUri = documentContentUri;
    }
}
