package org.rha.services.document_generation.core.docx;

import fr.opensagres.xdocreport.converter.*;
import fr.opensagres.xdocreport.core.document.DocumentKind;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.rha.services.document_generation.core.DocumentFormatConverter;
import org.rha.services.document_generation.core.model.exceptions.DocumentConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.InputStream;
import java.io.OutputStream;

@Named("docx-to-pdf-converter")
@ApplicationScoped
public class DocxToPdfDocumentFormatConverter implements DocumentFormatConverter {
    Logger logger = LoggerFactory.getLogger(DocxToPdfDocumentFormatConverter.class);

    @Override
    @Timed
    public void convert(InputStream inputStream, OutputStream outputStream) throws DocumentConversionException {
        try {
            final Options converterOptions = Options.getFrom(DocumentKind.DOCX).to(ConverterTypeTo.PDF);
            final IConverter converter = ConverterRegistry.getRegistry().findConverter(converterOptions);
            converter.convert(inputStream, outputStream, converterOptions);
        } catch (XDocConverterException e) {
            logger.error("An error occurred when converting a docx document to PDF", e);
            throw new DocumentConversionException(e);
        }
    }
}
