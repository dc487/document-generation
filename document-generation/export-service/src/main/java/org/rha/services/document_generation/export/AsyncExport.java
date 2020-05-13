package org.rha.services.document_generation.export;

import export.ExportDocumentRequestMessage;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import export.ExportDocumentSuccessMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class AsyncExport {

    @Inject
    IDocumentExporter documentExporter;

    Jsonb jsonb = JsonbBuilder.create();
    Logger logger = LoggerFactory.getLogger(AsyncExport.class);

    @Incoming("export-document-request")
    @Outgoing("export-document-response")
    @Acknowledgment(Acknowledgment.Strategy.POST_PROCESSING)
    @Transactional
    public CompletableFuture<ExportDocumentSuccessMessage> exportDocument(byte[] messageBytes) {
        final String message = new String(messageBytes, StandardCharsets.UTF_8);
        logger.info("Received export request with message: " + message);

        final ExportDocumentRequestMessage exportRequestMessage = jsonb.fromJson(message, ExportDocumentRequestMessage.class);

        return CompletableFuture.supplyAsync(() -> {
            try {
                return documentExporter.saveDocument(exportRequestMessage);
            } catch (Exception e) {
                //TODO: Send message to failure queue
                e.printStackTrace();
                return null;
            }
        })
                .thenApply(uri -> new ExportDocumentSuccessMessage(
                        uri,
                        exportRequestMessage.getExportSystemId(),
                        exportRequestMessage.getSourceSystemId(),
                        exportRequestMessage.getDocumentType(),
                        exportRequestMessage.getDocumentUrn(),
                        exportRequestMessage.getVersionId(),
                        exportRequestMessage.getTemplateName())

                )
                .exceptionally(e -> {
                    //TODO: Send message to failure queue
                    logger.error("Error occurred when trying to export document!");
                    return null;
                });
    }
}
