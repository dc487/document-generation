package org.rha.services.document_generation.v2;

import org.rha.services.document_generation.v2.dto.CreateVersionRequest;
import org.rha.services.document_generation.v2.dto.CreateVersionResponse;
import org.rha.services.document_generation.v2.dto.FindVersionsResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/versions")
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
