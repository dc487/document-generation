package org.rha.services.document_generation.sync;

import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.context.ThreadContext;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.rha.services.document_generation.core.DocumentGenerator;
import org.rha.services.document_generation.core.model.DocumentGenerationRequest;
import org.rha.services.document_generation.sync.dto.GenerateDocumentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.rha.services.document_generation.utils.LambdaExceptionUtils.rethrowFunction;

@Path("/api/document-generation")
@ApplicationScoped
public class DocumentGenerationResource {
    @Inject
    DocumentGenerator documentGenerator;

    @Inject
    ThreadContext threadContext;

    @Inject
    ManagedExecutor managedExecutor;

    Logger logger = LoggerFactory.getLogger(DocumentGenerationResource.class);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Counted
    public void generateDocument(@Suspended AsyncResponse asyncResponse,
                                 GenerateDocumentRequest generateDocumentRequest) throws Exception {
        CompletableFuture.supplyAsync(() -> new DocumentGenerationRequest(
                generateDocumentRequest.getDocumentTemplate(),
                generateDocumentRequest.getDocumentContent(),
                generateDocumentRequest
                        .getDocumentOutputFormats()
                        .stream()
                        .collect(Collectors.toMap(
                                f -> f,
                                f -> new ByteArrayOutputStream()
                        ))))
                .thenCompose(rethrowFunction(documentGenerator::generateDocuments))
                .thenApply(asyncResponse::resume)
                .exceptionally(e -> {
                    logger.error("An error occurred while generating the document", e);
                    return asyncResponse.resume(e);
                });
    }
}
