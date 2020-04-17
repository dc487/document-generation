package org.rha.services.document_generation.v2;

import org.rha.services.document_generation.db.DocumentHelper;
import org.rha.services.document_generation.db.dto.Document;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/templated-documents")
public class TemplatedDocumentsResource {

    DocumentHelper documentHelper = new DocumentHelper();

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
}
