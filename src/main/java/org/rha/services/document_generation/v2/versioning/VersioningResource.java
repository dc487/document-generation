package org.rha.services.document_generation.v2.versioning;

import org.rha.services.document_generation.v2.db.VersionHelper;
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
import java.util.List;

@Path("/api/versions")
@ApplicationScoped
public class VersioningResource {

    Logger logger = LoggerFactory.getLogger(VersioningResource.class);

    @Inject
    VersioningService versioningService;

    @Inject
    VersionHelper versionHelper;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CreateVersionResponse createVersion(CreateVersionRequest createVersionRequest) {

        versioningService.saveVersionAndChildDocs(createVersionRequest);

        //TODO: Place messages on queues based on pipeline request

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
}
