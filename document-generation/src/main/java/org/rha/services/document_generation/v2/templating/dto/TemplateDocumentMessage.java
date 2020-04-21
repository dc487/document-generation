package org.rha.services.document_generation.v2.templating.dto;

import java.net.URI;

public class TemplateDocumentMessage {
    private URI documentContentUri;
    private URI documentTemplateUri;
    private String sourceSystemId;
    private String documentType;
    private String documentUrn;

    public URI getDocumentContentUri() {
        return documentContentUri;
    }

    public void setDocumentContentUri(final URI documentContentUri) {
        this.documentContentUri = documentContentUri;
    }

    public URI getDocumentTemplateUri() {
        return documentTemplateUri;
    }

    public void setDocumentTemplateUri(final URI documentTemplateUri) {
        this.documentTemplateUri = documentTemplateUri;
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
}
