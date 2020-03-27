package org.rha.services.document_generation.core.docx;

import org.rha.services.document_generation.core.DocumentFormatConverter;
import org.rha.services.document_generation.core.DocumentGenerator;
import org.rha.services.document_generation.core.DocumentTemplater;
import org.rha.services.document_generation.core.model.DocumentGenerationRequest;
import org.rha.services.document_generation.core.model.exceptions.DocumentConversionException;
import org.rha.services.document_generation.core.model.exceptions.DocumentTemplatingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static org.rha.services.document_generation.utils.LambdaExceptionUtils.rethrowConsumer;

@ApplicationScoped
public class DocxDocumentGenerator implements DocumentGenerator {
    @Inject
    @Named("docx4j")
    DocumentTemplater documentTemplater;

    @Inject
    @Named("noop-converter")
    DocumentFormatConverter toDocxFormatConverter;

    @Inject
    @Named("docx-to-pdf-converter")
    DocumentFormatConverter toPdfFormatConverter;

    Logger logger = LoggerFactory.getLogger(DocxDocumentGenerator.class);

    @Override
    public void generateDocument(DocumentGenerationRequest documentGenerationRequest)
            throws DocumentConversionException, DocumentTemplatingException {
        final ByteArrayOutputStream templatedOutput = new ByteArrayOutputStream();
        logger.info("About to template");
        this.documentTemplater.populateDocumentTemplate(
                documentGenerationRequest.getDocumentTemplate(),
                documentGenerationRequest.getDocumentContent(),
                templatedOutput
        );
        logger.info("Finished templating");
        final byte[] templatedOutputBytes = templatedOutput.toByteArray();

        documentGenerationRequest.getOutputDocuments().entrySet().parallelStream()
                .forEach(rethrowConsumer(f -> {
                    logger.info("about to convert document");
                    final InputStream inputStream = new ByteArrayInputStream(templatedOutputBytes);
                    switch (f.getKey()) {
                        case DOCX:
                            this.toDocxFormatConverter.convert(inputStream, f.getValue());
                            break;
                        case PDF:
                            this.toPdfFormatConverter.convert(inputStream, f.getValue());
                            break;
                        default:
                            throw new DocumentConversionException("Unsupported output document format: " +
                                    f.getKey().name());
                    }
                }));
    }
}
