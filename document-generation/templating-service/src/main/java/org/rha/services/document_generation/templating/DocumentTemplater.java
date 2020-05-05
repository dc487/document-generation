package org.rha.services.document_generation.templating;

import org.rha.services.document_generation.templating.exceptions.DocumentTemplatingException;

import java.io.InputStream;
import java.io.OutputStream;

public interface DocumentTemplater {
    void populateDocumentTemplate(InputStream templateInputStream, InputStream contentInputStream,
                                  OutputStream outputStream) throws DocumentTemplatingException;
}
