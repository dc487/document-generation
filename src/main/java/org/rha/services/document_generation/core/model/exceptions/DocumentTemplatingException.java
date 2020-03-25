package org.rha.services.document_generation.core.model.exceptions;

public final class DocumentTemplatingException extends Exception {
    public DocumentTemplatingException(String message) {
        super(message);
    }

    public DocumentTemplatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentTemplatingException(Throwable cause) {
        super(cause);
    }
}
