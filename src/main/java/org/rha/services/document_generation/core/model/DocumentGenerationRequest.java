package org.rha.services.document_generation.core.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class DocumentGenerationRequest {
    private final URI documentTemplateUri;
    private final URI documentContentUri;
    private final Map<DocumentOutputFormat, OutputStream> outputDocuments;

    public DocumentGenerationRequest(
            final URI documentTemplateUri,
            final URI documentContentUri,
            final Map<DocumentOutputFormat, OutputStream> outputDocuments
    ) {
        if (documentTemplateUri == null)
            throw new IllegalArgumentException("documentTemplate cannot be null");

        if (documentContentUri == null)
            throw new IllegalArgumentException("documentContent cannot be null");

        this.documentTemplateUri = documentTemplateUri;
        this.documentContentUri = documentContentUri;

        this.outputDocuments = Objects.requireNonNullElse(outputDocuments, Collections.emptyMap());
    }

    public URI getDocumentTemplateUri() {
        return this.documentTemplateUri;
    }

    public URI getDocumentContentUri() {
        return this.documentContentUri;
    }

    public Map<DocumentOutputFormat, OutputStream> getOutputDocuments() {
        return this.outputDocuments;
    }
}
