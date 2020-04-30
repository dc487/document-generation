package org.rha.services.document_generation.export;

public class DocumentExportException extends Exception {
    public DocumentExportException(String message) {
        super(message);
    }

    public DocumentExportException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentExportException(Throwable cause) {
        super(cause);
    }
}
