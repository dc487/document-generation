package org.rha.services.document_generation.conversion.core;

import org.rha.services.document_generation.conversion.core.model.exceptions.DocumentConversionException;

import java.io.InputStream;
import java.io.OutputStream;

public interface DocumentFormatConverter {
    void convert(InputStream inputStream, OutputStream outputStream) throws DocumentConversionException;
}
