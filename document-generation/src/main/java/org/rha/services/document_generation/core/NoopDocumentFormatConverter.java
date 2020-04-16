package org.rha.services.document_generation.core;

import org.eclipse.microprofile.metrics.annotation.Timed;
import org.rha.services.document_generation.core.model.exceptions.DocumentConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Named("noop-converter")
@ApplicationScoped
public class NoopDocumentFormatConverter implements DocumentFormatConverter {
    Logger logger = LoggerFactory.getLogger(NoopDocumentFormatConverter.class);

    @Override
    @Timed
    public void convert(InputStream inputStream, OutputStream outputStream) throws DocumentConversionException {
        try {
            inputStream.transferTo(outputStream);
        } catch (IOException e) {
            logger.error("An error occurred when performing a no-op conversion", e);
            throw new DocumentConversionException(e);
        }
    }
}
