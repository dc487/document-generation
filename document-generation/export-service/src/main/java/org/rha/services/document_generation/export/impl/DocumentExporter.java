package org.rha.services.document_generation.export.impl;

import export.ExportDocumentRequestMessage;
import org.rha.services.document_generation.export.DocumentExportException;
import org.rha.services.document_generation.export.IDocumentExporter;
import org.rha.services.document_generation.export.IDocumentStore;
import org.rha.services.document_generation.export.dto.ExportSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.net.URI;

@ApplicationScoped
public class DocumentExporter implements IDocumentExporter {
    @Inject
    @Named("sharepoint-store")
    IDocumentStore sharePointStore;

    @Inject
    Client httpRequestClient;

    Logger logger = LoggerFactory.getLogger(DocumentExporter.class);

    @Override
    public URI saveDocument(ExportDocumentRequestMessage requestMessage) throws Exception {

        final InputStream documentContent = httpRequestClient.target(requestMessage.getDocumentContentUri())
                .request()
                .accept(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                .get(InputStream.class);

        switch (requestMessage.getExportSystemId()) {
            case ExportSystem.SHAREPOINT:
                logger.info("Starting SharePoint Export");
                return this.sharePointStore.saveDocument(documentContent, requestMessage.getExportMetadata());
            default:
                throw new DocumentExportException("Unsupported export system");
        }
    }
}
