package org.rha.services.document_generation.v2;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.rha.services.document_generation.v2.dto.TemplateDocumentMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Path("/api/templated-documents")
@ApplicationScoped
public class TemplatedDocumentsResource {

    Logger logger = LoggerFactory.getLogger(TemplatedDocumentsResource.class);

    Jsonb jsonb = JsonbBuilder.create();

    @GET
    @Path("{templatedDocumentId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getTemplatedDocument(@PathParam(value = "templatedDocumentId") Long templatedDocumentId) {
        return null;
    }

    @Incoming("template-document")
    public CompletionStage<Void> performDocumentTemplating(byte[] messageBytes)
            throws Exception {
        try {
            final String message = new String(messageBytes, "UTF-8");
            logger.info("Received message: " + message);
            final TemplateDocumentMessage templateDocumentMessage = jsonb.fromJson(message, TemplateDocumentMessage.class);

            // check to see if valid message

            // perform templating

            // send success message. Routing key = SUCCESS.${sourceSystemId}.${documentType}.${documentUrn}

            return null;
        } catch (Exception e) {
            logger.error("An unexpected error occurred when performing document templating", e);
            // Send failure message. Routing key = FAILURE.${sourceSystemId}.${documentType}.${documentUrn}
            return null;
        }
    }
}