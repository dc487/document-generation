package org.rha.services.document_generation.versioning;

import export.ExportDocumentRequestMessage;
import org.rha.services.document_generation.versioning.db.ChildDocumentHelper;
import org.rha.services.document_generation.versioning.db.VersionHelper;
import org.rha.services.document_generation.versioning.db.dto.ChildDocument;
import org.rha.services.document_generation.versioning.db.dto.Version;
import org.rha.services.document_generation.versioning.dto.CreateVersionRequest;
import org.rha.services.document_generation.versioning.dto.ResponseVersion;
import pipeline.dto.ExportPipelineStep;
import pipeline.dto.PipelineStep;
import pipeline.dto.TemplatePipelineStep;
import templating.TemplateDocumentMessage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class VersioningService {

    @Inject
    Client httpRequestClient;

    @Inject
    VersionHelper versionHelper;

    @Inject
    ChildDocumentHelper childDocumentHelper;

    /**
     * Retrieves the content from the specified version's document content URI, returning a response containing the content
     * stream if found.
     * @param version the version to retrieve the document content for
     * @return a Response containing the stream of the document content if found, or an error response if not
     */
    public Response getDocumentContent(Version version) {

        try {
            InputStream documentStream = httpRequestClient.target(version.getDocumentContentUri())
                    .request()
                    .accept(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                    .get(InputStream.class);
            return Response.ok(documentStream).build();
        }catch (ProcessingException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Could not download file from " + version.getDocumentContentUri()).build();
        }
    }

    /**
     * Saves a new version to the database from the information in the request, then saves child document entries for
     * each templating step in the request's pipelines.
     * @param createVersionRequest the new version request from a POST request received on the versions endpoint
     */
    public Version saveVersionAndChildDocs(CreateVersionRequest createVersionRequest) {
        // Save version information to DB
        Version version = versionHelper.saveVersion(createVersionRequest);

        // Save new child documents to database for each templating step in the request
        childDocumentHelper.saveAllChildDocuments(createVersionRequest, version);

        return version;
    }

    /**
     * Retrieves all versions from the database matching the specified parameters, and then collects a list of the
     * associated templated document types and their URIs
     * @return a list of ResponseVersion objects representing the retrieved versions and their templated document details
     */
    public List<ResponseVersion> findAllVersionsAndTemplateUris(String sourceSystemId, String documentType, String documentUrn) {
        List<ResponseVersion> responseVersions = new ArrayList<>();

        // Get all matching versions from DB
        List<Version> versions = versionHelper.getAllVersionsMatching(sourceSystemId, documentType, documentUrn);

        for (Version version : versions) {
            // For each of the versions, get their associated child documents and create a map of doc type and
            // doc URI from them
            Map<String, URI> templatedDocs = new HashMap<>();
            for (ChildDocument childDocument : version.getChildDocuments()) {
                URI documentUri = childDocument.getChildDocumentUri() == null ? URI.create("") : URI.create(childDocument.getChildDocumentUri());
                templatedDocs.put(childDocument.getTemplateName(), documentUri);
            }

            // Add new response version (version detail + templated docs list) to response list
            responseVersions.add(new ResponseVersion(
                    version.getVersionId(),
                    version.getSourceSystemId(),
                    version.getDocumentType(),
                    version.getDocumentUrn(),
                    templatedDocs));
        }

        return responseVersions;
    }

    /**
     * Updates the document URI for the specified version and file type
     * @param versionId the ID of the version's child documents to update
     * @param templateName the template name of the associated document
     * @param docUri the new value for the document URI
     */
    public void updateChildDocumentUri(Long versionId, String templateName, String docUri) {
        Version version = versionHelper.getVersionById(versionId);
        if (version != null) {
            childDocumentHelper.updateDocumentUriForVersionAndTemplateName(version, templateName, docUri);
        }
    }

    //TODO: Replace this with PipelineProcessors? (currently in shared libs temp)
    public void generateMessagesFromPipelines(Version version, Map<String, List<PipelineStep>> processingPipelines) {

        for (Map.Entry<String, List<PipelineStep>> entry : processingPipelines.entrySet()) {

            // Get the first pipeline:
            PipelineStep pipelineStep = entry.getValue().get(0);

            switch (pipelineStep.getPipelineStep()) {
                case TEMPLATE:
                    sendTemplatingMessage(version, entry, (TemplatePipelineStep) pipelineStep);
                    break;
                case EXPORT:
                    sendExportMessage(version, (ExportPipelineStep) pipelineStep);
                    break;
            }
        }
    }

    private void sendTemplatingMessage(Version version, Map.Entry<String, List<PipelineStep>> entry, TemplatePipelineStep pipelineStep) {

        // Create the template document message
        TemplateDocumentMessage templateDocumentMessage = new TemplateDocumentMessage();
        templateDocumentMessage.setTemplateName(pipelineStep.getTemplateSystemId());
        templateDocumentMessage.setDocumentTemplateUri(pipelineStep.getDocumentTemplateUri());
        templateDocumentMessage.setSourceSystemId(version.getSourceSystemId());
        templateDocumentMessage.setDocumentType(version.getDocumentType());
        templateDocumentMessage.setDocumentUrn(version.getDocumentUrn());
        templateDocumentMessage.setDocumentContentUri(URI.create(version.getDocumentContentUri()));
        templateDocumentMessage.setVersionId(version.getVersionId());

        List<PipelineStep> templatingPipelines = new ArrayList<>();

        // If there are more steps in the pipeline, set the pipeline (with the current step removed)
        if (entry.getValue().size() > 1) {
            List<PipelineStep> pipelineSteps = entry.getValue();
            pipelineSteps.remove(0);

            templatingPipelines = pipelineSteps;
        }
        templateDocumentMessage.setProcessingPipelines(templatingPipelines);

        //TODO: Send message to queue
    }

    private void sendExportMessage(Version version, ExportPipelineStep pipelineStep) {

        // Create the template document message
        ExportDocumentRequestMessage exportDocumentRequestMessage = new ExportDocumentRequestMessage();
        exportDocumentRequestMessage.setDocumentContentUri(URI.create(version.getDocumentContentUri()));
        exportDocumentRequestMessage.setExportSystemId(pipelineStep.getExportSystemId());
        exportDocumentRequestMessage.setExportMetadata(pipelineStep.getExportMetadata());
        exportDocumentRequestMessage.setSourceSystemId(version.getSourceSystemId());
        exportDocumentRequestMessage.setDocumentType(version.getDocumentType());
        exportDocumentRequestMessage.setDocumentUrn(version.getDocumentUrn());
        exportDocumentRequestMessage.setVersionId(version.getVersionId());

        //TODO: Send message to queue
    }
}
