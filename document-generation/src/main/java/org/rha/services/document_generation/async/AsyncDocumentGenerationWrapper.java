package org.rha.services.document_generation.async;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.rha.services.document_generation.async.dto.GenerateDocumentRequestMessage;
import org.rha.services.document_generation.async.dto.GenerateDocumentResponseMessage;
import org.rha.services.document_generation.core.DocumentGenerator;
import org.rha.services.document_generation.core.model.DocumentGenerationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import javax.ws.rs.client.Client;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.rha.services.document_generation.utils.LambdaExceptionUtils.rethrowFunction;

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
    @Transactional
    @Counted
    public CompletableFuture<GenerateDocumentResponseMessage> generateDocuments(byte[] messageBytes)
            throws Exception {
        final String message = new String(messageBytes, "UTF-8");
        logger.info("Received message: " + message);
        final GenerateDocumentRequestMessage request = jsonb.fromJson(message, GenerateDocumentRequestMessage.class);

        return CompletableFuture.supplyAsync(() -> new DocumentGenerationRequest(
                request.getDocumentTemplate(),
                request.getDocumentContent(),
                request
                        .getDocumentOutputFormats()
                        .stream()
                        .collect(Collectors.toMap(
                                f -> f,
                                f -> new ByteArrayOutputStream()
                        ))))
                .thenCompose(rethrowFunction(documentGenerator::generateDocuments))
                .thenApply(GenerateDocumentResponseMessage::new)
                .exceptionally(e -> {
                    logger.error("An error occurred while generating the document", e);
                    return null;
                });
    }
}
