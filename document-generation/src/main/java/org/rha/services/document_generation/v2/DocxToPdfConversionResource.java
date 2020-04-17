package org.rha.services.document_generation.v2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/api/docx-to-pdf-conversion")
public class DocxToPdfConversionResource {

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response convertDocxDocumentToPdf(@QueryParam(value = "docxDocumentUri") URI docxDocumentUri) {
        return null;
    }
}
