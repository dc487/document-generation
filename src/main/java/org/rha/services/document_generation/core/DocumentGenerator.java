package org.rha.services.document_generation.core;

import org.rha.services.document_generation.core.model.DocumentGenerationRequest;
import org.rha.services.document_generation.core.model.exceptions.DocumentConversionException;
import org.rha.services.document_generation.core.model.exceptions.DocumentTemplatingException;

public interface DocumentGenerator {
    void generateDocument(final DocumentGenerationRequest documentGenerationRequest)
            throws DocumentConversionException, DocumentTemplatingException;
}
