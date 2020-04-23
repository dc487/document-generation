package org.rha.services.document_generation.v2.versioning;

import org.rha.services.document_generation.v2.db.VersionHelper;
import org.rha.services.document_generation.v2.db.dto.Version;
import org.rha.services.document_generation.v2.versioning.dto.CreateVersionRequest;
import org.rha.services.document_generation.v2.versioning.dto.CreateVersionResponse;
import org.rha.services.document_generation.v2.versioning.dto.FindVersionsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;

@Path("/api/versions")
@ApplicationScoped
public class VersioningResource {

    Logger logger = LoggerFactory.getLogger(VersioningResource.class);

    private VersionHelper versionHelper = new VersionHelper();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CreateVersionResponse createVersion(CreateVersionRequest createVersionRequest) {

        // Save to DB
        Version version = new Version(
                createVersionRequest.getVersioning().getSourceSystemId(),
                createVersionRequest.getVersioning().getDocumentType(),
                createVersionRequest.getVersioning().getDocumentUrn(),
                createVersionRequest.getVersioning().getDocumentContentUri().toString(),
                "LEVEL_3",
                LocalDate.now().minusDays(1L));

        versionHelper.saveVersion(version);

        return null;
    }

    @GET
    @Path("{sourceSystemId}/{documentType}/{documentUrn}")
    @Produces(MediaType.APPLICATION_JSON)
    public FindVersionsResponse findVersions(
            @PathParam(value = "sourceSystemId") String sourceSystemId,
            @PathParam(value = "documentType") String documentType,
            @PathParam(value = "documentUrn") String documentUrn) {
        List<Version> versions = versionHelper.getAllVersionsMatching(sourceSystemId, documentType, documentUrn);

        return null;
    }

    @GET
    @Path("{versionId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getVersion(@PathParam(value = "versionId") Long versionId) {
        return null;
    }
}
