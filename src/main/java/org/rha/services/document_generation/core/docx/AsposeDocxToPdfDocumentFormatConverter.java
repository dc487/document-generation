package org.rha.services.document_generation.core.docx;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.rha.services.document_generation.core.DocumentFormatConverter;
import org.rha.services.document_generation.core.model.exceptions.DocumentConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.InputStream;
import java.io.OutputStream;

@Named("aspose-docx-to-pdf-converter")
@ApplicationScoped
public class AsposeDocxToPdfDocumentFormatConverter implements DocumentFormatConverter {
    Logger logger = LoggerFactory.getLogger(AsposeDocxToPdfDocumentFormatConverter.class);

    @Override
    @Timed
    public void convert(InputStream inputStream, OutputStream outputStream) throws DocumentConversionException {
        try {
            final Document document = new Document(inputStream);
            document.save(outputStream, SaveFormat.PDF);
        } catch (Exception e) {
            logger.error("An error occurred when converting a docx document to PDF", e);
            throw new DocumentConversionException(e);
        }
    }
}
