package org.rha.services.document_generation.core.docx;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.rha.services.document_generation.core.DocumentTemplater;
import org.rha.services.document_generation.core.model.exceptions.DocumentTemplatingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.io.InputStream;
import java.io.OutputStream;

@Named("docx4j")
@ApplicationScoped
public class Docx4jDocumentTemplater implements DocumentTemplater {
    Logger logger = LoggerFactory.getLogger(Docx4jDocumentTemplater.class);

    @Override
    public void populateDocumentTemplate(InputStream templateInputStream, InputStream contentInputStream,
                                         OutputStream outputStream) throws DocumentTemplatingException {
        try {
            final WordprocessingMLPackage template = Docx4J.load(templateInputStream);
            Docx4J.bind(template, contentInputStream, Docx4J.FLAG_NONE);
            Docx4J.save(template, outputStream);
        } catch (Docx4JException e) {
            logger.error("An error occurred during Docx4J document templating", e);
            throw new DocumentTemplatingException(e);
        }
    }
}
