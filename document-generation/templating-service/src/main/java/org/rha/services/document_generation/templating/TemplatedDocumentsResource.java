package org.rha.services.document_generation.templating;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.rha.services.document_generation.templating.db.DocumentHelper;
import org.rha.services.document_generation.templating.db.dto.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import templating.DeleteTemplatedDocumentMessage;
import templating.TemplateDocumentMessage;
import templating.TemplateMessageValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/api/templated-documents")
@ApplicationScoped
public class TemplatedDocumentsResource {

    @Inject
    TemplatedDocumentsService documentsService;

    @Inject
    DocumentHelper documentHelper;

    @Inject
    TemplateMessageValidator validator;

    Logger logger = LoggerFactory.getLogger(TemplatedDocumentsResource.class);

    Jsonb jsonb = JsonbBuilder.create();

    @GET
    @Path("{templatedDocumentId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getTemplatedDocument(@PathParam(value = "templatedDocumentId") Long templatedDocumentId) {

        Document document = documentHelper.getDocumentById(templatedDocumentId.intValue());

        if (document == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.ok(document.getContent()).build();
        }

    }

    /**
     * Listens for template request messages.
     */
    @Incoming("template-document")
    public void performDocumentTemplating(byte[] messageBytes)
            throws Exception {
        try {
            final String message = new String(messageBytes, "UTF-8");
            logger.info("Received message: " + message);
            TemplateDocumentMessage templateDocumentMessage = jsonb.fromJson(message, TemplateDocumentMessage.class);

            // check to see if valid message
            Set<String> violations = validator.validateMessage(templateDocumentMessage);

            if (violations.isEmpty()) {
                // perform templating
                documentsService.templateDocument(
                        templateDocumentMessage.getDocumentTemplateUri(),
                        templateDocumentMessage.getDocumentContentUri(),
                        templateDocumentMessage.getDocumentType());

                // TODO: send success message. Routing key = SUCCESS.${sourceSystemId}.${documentType}.${documentUrn}
            }
            else {
                logger.info("### MESSAGE VALIDATION FAILED! ###");
                violations.forEach(
                        v -> logger.info(v)
                );
                logger.info("### MESSAGE VALIDATION FAILED! ###");
            }
        } catch (Exception e) {
            logger.error("An unexpected error occurred when performing document templating", e);
            // TODO: Send failure message. Routing key = FAILURE.${sourceSystemId}.${documentType}.${documentUrn}
        }
    }

    /**
     * Deletes the specified templated document from the database
     */
    @Incoming("delete-templated-document")
    public void deleteTemplatedDocOnExport(byte[] messageBytes) {
        try {
            final String message = new String(messageBytes, "UTF-8");
            logger.info("Received message: " + message);
            final DeleteTemplatedDocumentMessage deleteMessage = jsonb.fromJson(message, DeleteTemplatedDocumentMessage.class);

            // check to see if valid message

            // perform templating
            documentsService.deleteTemplatedDocument(deleteMessage.getTemplatedDocumentId());

            // TODO: send success message. Routing key = SUCCESS.${sourceSystemId}.${documentType}.${documentUrn}

        } catch (Exception e) {
            logger.error("An unexpected error occurred when performing document templating", e);
            // TODO: Send failure message. Routing key = FAILURE.${sourceSystemId}.${documentType}.${documentUrn}
        }

    }
}
