package org.rha.services.document_generation.sync.dto;

import org.rha.services.document_generation.core.model.DocumentOutputFormat;

import java.net.URI;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class GenerateDocumentResponse {
    private Map<DocumentOutputFormat, URI> generatedDocuments = Collections.emptyMap();

    public Map<DocumentOutputFormat, URI> getGeneratedDocuments() {
        return generatedDocuments;
    }

    public void setGeneratedDocuments(Map<DocumentOutputFormat, URI> generatedDocuments) {
        this.generatedDocuments = Objects.requireNonNullElse(generatedDocuments, Collections.emptyMap());
    }
}
