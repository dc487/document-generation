package org.rha.services.document_generation.async.dto;

import org.rha.services.document_generation.core.model.DocumentOutputFormat;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GenerateDocumentRequestMessage {
    private URI documentTemplate;
    private URI documentContent;
    private List<DocumentOutputFormat> documentOutputFormats = Collections.emptyList();

    public URI getDocumentTemplate() {
        return documentTemplate;
    }

    public void setDocumentTemplate(final URI documentTemplate) {
        this.documentTemplate = documentTemplate;
    }

    public URI getDocumentContent() {
        return documentContent;
    }

    public void setDocumentContent(final URI documentContent) {
        this.documentContent = documentContent;
    }

    public List<DocumentOutputFormat> getDocumentOutputFormats() {
        return documentOutputFormats;
    }

    public void setDocumentOutputFormats(final List<DocumentOutputFormat> documentOutputFormats) {
        this.documentOutputFormats = Objects.requireNonNullElse(documentOutputFormats, Collections.emptyList());
    }
}
