package org.rha.services.document_generation.templating.dto;

import java.net.URI;

public class DocumentTemplatingFailureMessage {
    private String failureReason;
    private URI documentContentUri;
    private URI documentTemplateUri;
    private String sourceSystemId;
    private String documentType;
    private String documentUrn;
    private String templateName;

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(final String failureReason) {
        this.failureReason = failureReason;
    }

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

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(final String templateName) {
        this.templateName = templateName;
    }
}
