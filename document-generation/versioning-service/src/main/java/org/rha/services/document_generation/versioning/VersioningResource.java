package org.rha.services.document_generation.versioning;

import export.ExportDocumentSuccessMessage;
import export.ExportSystem;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.rha.services.document_generation.versioning.db.VersionHelper;
import org.rha.services.document_generation.versioning.db.dto.Version;
import org.rha.services.document_generation.versioning.dto.CreateVersionRequest;
import org.rha.services.document_generation.versioning.dto.CreateVersionResponse;
import org.rha.services.document_generation.versioning.dto.FindVersionsResponse;
import org.rha.services.document_generation.versioning.dto.ResponseVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import templating.DocumentTemplatingSuccessMessage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/versions")
@ApplicationScoped
public class VersioningResource {

    Logger logger = LoggerFactory.getLogger(VersioningResource.class);

    @Inject
    VersioningService versioningService;

    @Inject
    VersionHelper versionHelper;

    @Inject @Channel("test-channel")
    Emitter<CreateVersionRequest> emitter;

    Jsonb jsonb = JsonbBuilder.create();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CreateVersionResponse createVersion(CreateVersionRequest createVersionRequest) {

        Version version = versioningService.saveVersionAndChildDocs(createVersionRequest);

        //TODO: Place messages on queues based on pipeline request
        versioningService.generateMessagesFromPipelines(version, createVersionRequest.getProcessingPipelines());

        logger.info("Sending message to rabbit");

        //versioningService.sendPipelineMessage(createVersionRequest);

        //emitter.send(createVersionRequest);


        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public FindVersionsResponse findVersions(
            @QueryParam(value = "sourceSystemId") String sourceSystemId,
            @QueryParam(value = "documentType") String documentType,
            @QueryParam(value = "documentUrn") String documentUrn) {

        List<ResponseVersion> responseVersions = versioningService.findAllVersionsAndTemplateUris(sourceSystemId, documentType, documentUrn);

        return new FindVersionsResponse(responseVersions);
    }

    @GET
    @Path("{versionId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getVersion(@PathParam(value = "versionId") Long versionId) {
        Version version = versionHelper.getVersionById(versionId);

        if (version == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return versioningService.getDocumentContent(version);
    }

    /**
     * Listens for export success messages. If the export system was sharepoint, updates the child document in the
     * database with the new exported document URI and sends a message to the templating service to delete the
     * templated document
     */
    @Incoming("export-document-response")
    public void updateTemplatedUriOnExport(byte[] messageBytes) {

        try {
            logger.info("New EXPORT SUCCESS MESSAGE!");
            final String message;
            message = new String(messageBytes, "UTF-8");
            final ExportDocumentSuccessMessage successMsg = jsonb.fromJson(message, ExportDocumentSuccessMessage.class);

            // Only update and delete if the export system is sharepoint and the message contains a version ID
            if (successMsg.getExportSystemId().equals(ExportSystem.SHAREPOINT) && successMsg.getVersionId() != null) {
                // Update the child document with the new URI
                versioningService.updateChildDocumentUri(
                        successMsg.getVersionId(),
                        successMsg.getTemplateName(),
                        successMsg.getExportedFileUri().toString()
                );

            /*
             TODO: get the templated document ID, send delete message to templating service to delete
              templated doc from database
            */
            }

        } catch (Exception e) {
            logger.error("An unexpected error occurred when updating templated document URI", e);

            //TODO: Error queue message?
        }
    }

    /**
     * Listens for templating success messages. Updates the relevant child document in the database with the new
     * templated document URI
     */
    @Incoming("template-document-response")
    public void updateTemplatedUriOnTemplate(byte[] messageBytes) {

        try {
            logger.info("New TEMPLATE SUCCESS MESSAGE!");
            final String message;
            message = new String(messageBytes, "UTF-8");
            final DocumentTemplatingSuccessMessage successMsg = jsonb.fromJson(message, DocumentTemplatingSuccessMessage.class);

            // Only update the URI if the message contains a versionId
            if (successMsg.getVersionId() != null) {
                versioningService.updateChildDocumentUri(
                        successMsg.getVersionId(),
                        successMsg.getTemplateName(),
                        successMsg.getTemplatedDocumentUri().toString()
                );
            }
        } catch (Exception e) {
            logger.error("An unexpected error occurred when updating templated document URI", e);

            //TODO: Error queue message?
        }
    }
}
