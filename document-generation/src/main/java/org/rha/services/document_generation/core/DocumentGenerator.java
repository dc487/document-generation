package org.rha.services.document_generation.core;

import org.rha.services.document_generation.core.model.DocumentGenerationRequest;
import org.rha.services.document_generation.core.model.exceptions.DocumentConversionException;
import org.rha.services.document_generation.core.model.exceptions.DocumentTemplatingException;

import java.util.concurrent.CompletableFuture;

public interface DocumentGenerator {
    CompletableFuture<Void> generateDocument(final DocumentGenerationRequest documentGenerationRequest) throws Exception;
}
