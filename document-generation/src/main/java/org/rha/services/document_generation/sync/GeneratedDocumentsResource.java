package org.rha.services.document_generation.sync;

import org.rha.services.document_generation.db.DBHelper;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Path("/api/generated-docs")
@ApplicationScoped
public class GeneratedDocumentsResource {
    DBHelper dbHelper = new DBHelper();

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getDocument(@QueryParam("name") String documentName) throws IOException {
        File file = new File(documentName + ".docx");
        FileOutputStream fos;
        fos = new FileOutputStream(file);
        fos.write(dbHelper.getDocumentByName(documentName).getContent());
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM).build();
    }
}
