package org.rha.services.document_generation.conversion;

import org.rha.services.document_generation.conversion.core.DocumentFormatConverter;
import org.rha.services.document_generation.conversion.core.model.exceptions.DocumentConversionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Path("/api/docx-to-pdf-conversion")
@ApplicationScoped
public class DocxToPdfConversionResource {

    @Inject
    @Named("aspose-docx-to-pdf-converter")
    DocumentFormatConverter documentFormatConverter;

    @Inject
    Client httpClient;

    Logger logger = LoggerFactory.getLogger(DocxToPdfConversionResource.class);

    @GET
    @Produces("application/pdf")
    public void convertDocxDocumentToPdf(@QueryParam(value = "originalDocumentUri") URI originalDocumentUri,
                                         @Suspended AsyncResponse asyncResponse) {
        if (originalDocumentUri == null || !originalDocumentUri.isAbsolute()) {
            logger.error("Invalid originalDocumentUri supplied");
            asyncResponse.resume(
                    new WebApplicationException(
                            "Invalid originalDocumentUri supplied",
                            Response.Status.BAD_REQUEST));
            return;
        } else if (!"http".equals(originalDocumentUri.getScheme()) && !"https".equals(originalDocumentUri.getScheme())) {
            logger.error("Invalid originalDocumentUri supplied. The supplied URI must use either the http or https protocol.");
            asyncResponse.resume(
                    new WebApplicationException(
                            "Invalid originalDocumentUri supplied. The supplied URI must use either the http or https protocol.",
                            Response.Status.BAD_REQUEST));
            return;
        }

        final CompletableFuture<InputStream> docxDocument = httpClient
                .target(originalDocumentUri)
                .request()
                .rx()
                .get(InputStream.class)
                .toCompletableFuture();

        docxDocument
                .thenApply(
                        inputStream -> (StreamingOutput) outputStream -> {
                            try {
                                documentFormatConverter.convert(inputStream, outputStream);
                            } catch (DocumentConversionException e) {
                                logger.error("An error occurred when converting the DOCX document to PDF", e);
                                throw new WebApplicationException(
                                        "An error occurred when converting the DOCX document to PDF",
                                        e,
                                        Response.Status.INTERNAL_SERVER_ERROR);
                            }
                        })
                .thenAccept(streamingOutput -> asyncResponse.resume(Response.ok(streamingOutput).build()))
                .exceptionally(e -> {
                    logger.error("An error occurred during document conversion", e);
                    asyncResponse.resume(
                            new WebApplicationException(
                                    "An error occurred during document conversion",
                                    e,
                                    Response.Status.INTERNAL_SERVER_ERROR));
                    return null;
                });
    }
}
