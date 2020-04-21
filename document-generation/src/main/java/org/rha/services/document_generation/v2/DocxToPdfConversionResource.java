package org.rha.services.document_generation.v2;

import org.rha.services.document_generation.core.DocumentFormatConverter;
import org.rha.services.document_generation.core.model.exceptions.DocumentConversionException;
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
    public void convertDocxDocumentToPdf(@QueryParam(value = "docxDocumentUri") URI docxDocumentUri,
                                         @Suspended AsyncResponse asyncResponse) {
        if (docxDocumentUri == null || !docxDocumentUri.isAbsolute()) {
            logger.error("Invalid docxDocumentUri supplied");
            asyncResponse.resume(
                    new WebApplicationException(
                            "Invalid docxDocumentUri supplied",
                            Response.Status.BAD_REQUEST));
            return;
        } else if (!"http".equals(docxDocumentUri.getScheme()) && !"https".equals(docxDocumentUri.getScheme())) {
            logger.error("Invalid docxDocumentUri supplied. The supplied URI must use either the http or https protocol.");
            asyncResponse.resume(
                    new WebApplicationException(
                            "Invalid docxDocumentUri supplied. The supplied URI must use either the http or https protocol.",
                            Response.Status.BAD_REQUEST));
            return;
        }

        final CompletableFuture<InputStream> docxDocument = httpClient
                .target(docxDocumentUri)
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
