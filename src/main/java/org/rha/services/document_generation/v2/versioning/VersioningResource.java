package org.rha.services.document_generation.v2.versioning;

import org.rha.services.document_generation.v2.db.ChildDocumentHelper;
import org.rha.services.document_generation.v2.db.VersionHelper;
import org.rha.services.document_generation.v2.db.dto.ChildDocument;
import org.rha.services.document_generation.v2.db.dto.Version;
import org.rha.services.document_generation.v2.versioning.dto.CreateVersionRequest;
import org.rha.services.document_generation.v2.versioning.dto.CreateVersionResponse;
import org.rha.services.document_generation.v2.versioning.dto.FindVersionsResponse;
import org.rha.services.document_generation.v2.versioning.dto.ResponseVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/api/versions")
@ApplicationScoped
public class VersioningResource {

    Logger logger = LoggerFactory.getLogger(VersioningResource.class);

    @Inject
    VersioningService versioningService;

    @Inject
    VersionHelper versionHelper;

    @Inject
    ChildDocumentHelper childDocumentHelper;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CreateVersionResponse createVersion(CreateVersionRequest createVersionRequest) {

        // Save version information to DB
        Version version = versionHelper.saveVersion(createVersionRequest);

        // Save new child documents to database for each templating step in the request
        childDocumentHelper.saveAllChildDocuments(createVersionRequest, version);

        //TODO: Place messages on queues based on pipeline request

        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public FindVersionsResponse findVersions(
            @QueryParam(value = "sourceSystemId") String sourceSystemId,
            @QueryParam(value = "documentType") String documentType,
            @QueryParam(value = "documentUrn") String documentUrn) {

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
}
