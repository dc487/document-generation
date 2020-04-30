package org.rha.services.document_generation.conversion.core.model.exceptions;

public final class DocumentConversionException extends Exception {
    public DocumentConversionException(String message) {
        super(message);
    }

    public DocumentConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentConversionException(Throwable cause) {
        super(cause);
    }
}
