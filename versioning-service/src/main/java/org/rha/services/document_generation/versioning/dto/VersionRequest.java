package org.rha.services.document_generation.versioning.dto;

import javax.validation.constraints.NotNull;
import java.net.URI;

public class VersionRequest {

    @NotNull(message = "Must include Source System ID!")
    private String sourceSystemId;

    @NotNull(message = "Must include Document Type!")
    private String documentType;

    @NotNull(message = "Must include Document URN!")
    private String documentUrn;

    @NotNull(message = "Must include Document Content URI!")
    private URI documentContentUri;

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

    public URI getDocumentContentUri() {
        return documentContentUri;
    }

    public void setDocumentContentUri(final URI documentContentUri) {
        this.documentContentUri = documentContentUri;
    }


}
