package org.rha.services.document_generation.async;

import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.rha.services.document_generation.async.dto.GenerateDocumentRequestMessage;
import org.rha.services.document_generation.async.dto.GenerateDocumentResponseMessage;
import org.rha.services.document_generation.core.DocumentGenerator;
import org.rha.services.document_generation.core.model.DocumentGenerationRequest;
import org.rha.services.document_generation.core.model.DocumentOutputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static org.rha.services.document_generation.utils.LambdaExceptionUtils.*;

@ApplicationScoped
public class AsyncDocumentGenerationWrapper {

    @Inject
    DocumentGenerator documentGenerator;

    @Inject
    Client httpRequestClient;

    Jsonb jsonb = JsonbBuilder.create();

    Logger logger = LoggerFactory.getLogger(AsyncDocumentGenerationWrapper.class);

    @Incoming("generate-document-request")
    @Outgoing("generate-document-response")
    @Acknowledgment(Acknowledgment.Strategy.POST_PROCESSING)
    public CompletionStage<GenerateDocumentResponseMessage> generateDocuments(byte[] messageBytes)
            throws Exception {
        final String message = new String(messageBytes, "UTF-8");
        logger.info("Received message: " + message);
        final GenerateDocumentRequestMessage request = jsonb.fromJson(message, GenerateDocumentRequestMessage.class);

        final Map<DocumentOutputFormat, OutputStream> generatedDocuments =
                request.getDocumentOutputFormats()
                        .stream()
                        .collect(Collectors.toMap(k -> k, rethrowFunction(k -> new FileOutputStream("D:\\output." + k.name()))));

        final Future<InputStream> documentTemplateResponse = httpRequestClient
                .target(request.getDocumentTemplate())
                .request()
                .accept(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                .async()
                .get(InputStream.class);

        final Future<InputStream> documentContentResponse = httpRequestClient
                .target(request.getDocumentContent())
                .request()
                .accept(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                .async()
                .get(InputStream.class);

        return CompletableFuture
                .supplyAsync(rethrowSupplier(documentTemplateResponse::get))
                .thenCombine(CompletableFuture.supplyAsync(rethrowSupplier(documentContentResponse::get)),
                        (template, content) -> {
                            logger.info("got all of the requests");
                            return new DocumentGenerationRequest(
                                    template,
                                    content,
                                    generatedDocuments
                            );
                        })
                .thenAcceptAsync(rethrowConsumer(documentGenerationRequest -> documentGenerator.generateDocument(documentGenerationRequest)))
                .thenApply(rethrowFunction(x -> {
                    logger.info("I don't know what apply does");
                    generatedDocuments.values().forEach(rethrowConsumer(OutputStream::flush));
                    return new GenerateDocumentResponseMessage();
                })).exceptionally(e -> {
                    logger.error("An error occurred while processing message: " + message, e);
                    return null;
                });
    }
}
