package org.rha.services.document_generation.v2.versioning;

import org.rha.services.document_generation.v2.db.ChildDocumentHelper;
import org.rha.services.document_generation.v2.db.VersionHelper;
import org.rha.services.document_generation.v2.db.dto.ChildDocument;
import org.rha.services.document_generation.v2.db.dto.Version;
import org.rha.services.document_generation.v2.versioning.dto.CreateVersionRequest;
import org.rha.services.document_generation.v2.versioning.dto.ResponseVersion;

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
     * @param createVersionRequest the new version request from a POST request recieved on the versions endpoint
     */
    public void saveVersionAndChildDocs(CreateVersionRequest createVersionRequest) {
        // Save version information to DB
        Version version = versionHelper.saveVersion(createVersionRequest);

        // Save new child documents to database for each templating step in the request
        childDocumentHelper.saveAllChildDocuments(createVersionRequest, version);
    }

    /**
     * Retrieves all versions from the database matching the specified parameters, and then collects a list of the
     * associated templated document types and their URIs
     * @param sourceSystemId
     * @param documentType
     * @param documentUrn
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
                templatedDocs.put(childDocument.getChildDocumentFileType(), documentUri);
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
}
