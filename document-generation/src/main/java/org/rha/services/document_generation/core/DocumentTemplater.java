package org.rha.services.document_generation.core;

import org.rha.services.document_generation.core.model.exceptions.DocumentTemplatingException;

import java.io.InputStream;
import java.io.OutputStream;

public interface DocumentTemplater {
    void populateDocumentTemplate(InputStream templateInputStream, InputStream contentInputStream,
                                  OutputStream outputStream) throws DocumentTemplatingException;
}
