package org.rha.services.document_generation.v2.dto;

import java.net.URI;

public class DocumentTemplatingSuccessMessage {
    private Long documentTemplatingId;
    private URI templatedDocumentUri;
    private String sourceSystemId;
    private String documentType;
    private String documentUrn;
    private String templateName;

    public Long getDocumentTemplatingId() {
        return documentTemplatingId;
    }

    public void setDocumentTemplatingId(final Long documentTemplatingId) {
        this.documentTemplatingId = documentTemplatingId;
    }

    public URI getTemplatedDocumentUri() {
        return templatedDocumentUri;
    }

    public void setTemplatedDocumentUri(final URI templatedDocumentUri) {
        this.templatedDocumentUri = templatedDocumentUri;
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
