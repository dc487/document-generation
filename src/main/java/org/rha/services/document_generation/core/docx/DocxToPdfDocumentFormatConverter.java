package org.rha.services.document_generation.core.docx;

import fr.opensagres.xdocreport.converter.*;
import fr.opensagres.xdocreport.core.document.DocumentKind;
import org.rha.services.document_generation.core.DocumentFormatConverter;
import org.rha.services.document_generation.core.model.exceptions.DocumentConversionException;

import javax.inject.Named;
import java.io.InputStream;
import java.io.OutputStream;

@Named("docx-to-pdf-converter")
public final class DocxToPdfDocumentFormatConverter implements DocumentFormatConverter {
    @Override
    public void convert(InputStream inputStream, OutputStream outputStream) throws DocumentConversionException {
        try {
            final Options converterOptions = Options.getFrom(DocumentKind.DOCX).to(ConverterTypeTo.PDF);
            final IConverter converter = ConverterRegistry.getRegistry().findConverter(converterOptions);
            converter.convert(inputStream, outputStream, converterOptions);
        } catch (XDocConverterException e) {
            throw new DocumentConversionException(e);
        }
    }
}
