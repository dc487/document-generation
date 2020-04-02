package org.rha.services.document_generation.core.docx;

import org.rha.services.document_generation.core.DocumentFormatConverter;
import org.rha.services.document_generation.core.DocumentGenerator;
import org.rha.services.document_generation.core.DocumentTemplater;
import org.rha.services.document_generation.core.model.DocumentGenerationRequest;
import org.rha.services.document_generation.core.model.exceptions.DocumentConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

import static org.rha.services.document_generation.utils.LambdaExceptionUtils.rethrowBiConsumer;
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

    @Inject
    Client httpRequestClient;

    Logger logger = LoggerFactory.getLogger(DocxDocumentGenerator.class);

    @Override
    public CompletableFuture<Void> generateDocument(DocumentGenerationRequest documentGenerationRequest) throws Exception {

        final CompletableFuture<InputStream> documentTemplateResponse = httpRequestClient
                .target(documentGenerationRequest.getDocumentTemplateUri())
                .request()
                .accept(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                .rx()
                .get(InputStream.class)
                .toCompletableFuture();

        final CompletableFuture<InputStream> documentContentResponse = httpRequestClient
                .target(documentGenerationRequest.getDocumentContentUri())
                .request()
                .accept(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                .rx()
                .get(InputStream.class)
                .toCompletableFuture();

        return documentTemplateResponse
                .thenAcceptBothAsync(
                        documentContentResponse,
                        rethrowBiConsumer((template, content) -> {
                            logger.debug("Starting document templating");
                            final ByteArrayOutputStream templatedOutput = new ByteArrayOutputStream();
                            this.documentTemplater.populateDocumentTemplate(
                                    template,
                                    content,
                                    templatedOutput
                            );
                            final byte[] templatedOutputBytes = templatedOutput.toByteArray();
                            logger.debug("Finished document templating");

                            documentGenerationRequest.getOutputDocuments().entrySet().parallelStream()
                                    .forEach(rethrowConsumer(f -> {
                                        final InputStream inputStream = new ByteArrayInputStream(templatedOutputBytes);
                                        switch (f.getKey()) {
                                            case DOCX:
                                                logger.debug("Starting docx to docx conversion");
                                                this.toDocxFormatConverter.convert(inputStream, f.getValue());
                                                logger.debug("Finished docx to docx conversion");
                                                break;
                                            case PDF:
                                                logger.debug("Starting docx to PDF conversion");
                                                this.toPdfFormatConverter.convert(inputStream, f.getValue());
                                                logger.debug("Finished docx to PDF conversion");
                                                break;
                                            default:
                                                throw new DocumentConversionException("Unsupported output document format: " +
                                                        f.getKey().name());
                                        }
                                    }));
                        }));
    }
}
