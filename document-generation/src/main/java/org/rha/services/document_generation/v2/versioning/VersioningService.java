package org.rha.services.document_generation.v2.versioning;

import org.rha.services.document_generation.v2.db.dto.Version;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.net.UnknownHostException;

@ApplicationScoped
public class VersioningService {

    @Inject
    Client httpRequestClient;

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
}
