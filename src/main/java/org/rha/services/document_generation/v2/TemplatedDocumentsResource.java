package org.rha.services.document_generation.v2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/templated-documents")
public class TemplatedDocumentsResource {

    @GET
    @Path("{templatedDocumentId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getTemplatedDocument(@PathParam(value = "templatedDocumentId") Long templatedDocumentId) {
        return null;
    }
}
