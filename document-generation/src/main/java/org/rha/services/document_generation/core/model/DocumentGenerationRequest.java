package org.rha.services.document_generation.core.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class DocumentGenerationRequest {
    private final InputStream documentTemplate;
    private final InputStream documentContent;
    private final Map<DocumentOutputFormat, OutputStream> outputDocuments;

    public DocumentGenerationRequest(
            final InputStream documentTemplate,
            final InputStream documentContent,
            final Map<DocumentOutputFormat, OutputStream> outputDocuments
    ) {
        if (documentTemplate == null)
            throw new IllegalArgumentException("documentTemplate cannot be null");

        if (documentContent == null)
            throw new IllegalArgumentException("documentContent cannot be null");

        this.documentTemplate = documentTemplate;
        this.documentContent = documentContent;

        this.outputDocuments = Objects.requireNonNullElse(outputDocuments, Collections.emptyMap());
    }

    public InputStream getDocumentTemplate() {
        return this.documentTemplate;
    }

    public InputStream getDocumentContent() {
        return this.documentContent;
    }

    public Map<DocumentOutputFormat, OutputStream> getOutputDocuments() {
        return this.outputDocuments;
    }
}
