package org.rha.services.document_generation.core;

import org.rha.services.document_generation.core.model.exceptions.DocumentConversionException;

import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Named("noop-converter")
public final class NoopDocumentFormatConverter implements DocumentFormatConverter {
    @Override
    public void convert(InputStream inputStream, OutputStream outputStream) throws DocumentConversionException {
        try {
            inputStream.transferTo(outputStream);
        } catch (IOException e) {
            throw new DocumentConversionException(e);
        }
    }
}
