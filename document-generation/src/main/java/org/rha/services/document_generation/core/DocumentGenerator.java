package org.rha.services.document_generation.core;

import org.rha.services.document_generation.core.model.DocumentGenerationRequest;
import org.rha.services.document_generation.core.model.DocumentOutputFormat;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface DocumentGenerator {
    CompletableFuture<Map<DocumentOutputFormat, URI>> generateDocuments(final DocumentGenerationRequest documentGenerationRequest) throws Exception;
}
