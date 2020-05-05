package org.rha.services.document_generation.templating;

import org.rha.services.document_generation.templating.db.DocumentHelper;
import org.rha.services.document_generation.templating.db.dto.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.rha.services.document_generation.templating.utils.LambdaExceptionUtils.rethrowBiConsumer;

@ApplicationScoped
public class TemplatedDocumentsService {

    @Inject
    @Named("docx4j")
    DocumentTemplater documentTemplater;

    @Inject
    Client httpRequestClient;

    @Inject
    DocumentHelper documentHelper;

    Logger logger = LoggerFactory.getLogger(TemplatedDocumentsService.class);

    public void templateDocument(URI documentTemplateUri, URI documentContentUri, String documentType) throws Exception {

        final CompletableFuture<InputStream> documentTemplateResponse = httpRequestClient
                .target(documentTemplateUri)
                .request()
                .accept(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                .rx()
                .get(InputStream.class)
                .toCompletableFuture();

        final CompletableFuture<InputStream> documentContentResponse = httpRequestClient
                .target(documentContentUri)
                .request()
                .accept(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                .rx()
                .get(InputStream.class)
                .toCompletableFuture();

        documentTemplateResponse
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

                            documentHelper.saveDocument(new Document(UUID.randomUUID().toString(), templatedOutputBytes, "LEVEL_1", LocalDate.now().minusDays(1L)));
                        })
                );
    }
}
