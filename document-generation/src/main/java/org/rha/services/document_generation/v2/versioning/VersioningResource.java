package org.rha.services.document_generation.v2.versioning;

import org.rha.services.document_generation.v2.versioning.dto.CreateVersionRequest;
import org.rha.services.document_generation.v2.versioning.dto.CreateVersionResponse;
import org.rha.services.document_generation.v2.versioning.dto.FindVersionsResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/versions")
@ApplicationScoped
public class VersioningResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public CreateVersionResponse createVersion(CreateVersionRequest createVersionRequest) {
        return null;
    }

    @GET
    @Path("{sourceSystemId}/{documentType}/{documentUrn}")
    @Produces(MediaType.APPLICATION_JSON)
    public FindVersionsResponse findVersions(
            @PathParam(value = "sourceSystemId") String sourceSystemId,
            @PathParam(value = "documentType") String documentType,
            @PathParam(value = "documentUrn") String documentUrn) {
        return null;
    }

    @GET
    @Path("{versionId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getVersion(@PathParam(value = "versionId") Long versionId) {
        return null;
    }
}
