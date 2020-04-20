package org.rha.services.document_generation.v2.export.impl;

import org.rha.services.document_generation.v2.export.DocumentExportException;
import org.rha.services.document_generation.v2.export.IDocumentExporter;
import org.rha.services.document_generation.v2.export.IDocumentStore;
import org.rha.services.document_generation.v2.export.dto.ExportDocumentRequestMessage;
import org.rha.services.document_generation.v2.export.dto.ExportSystem;

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

    @Override
    public URI saveDocument(ExportDocumentRequestMessage requestMessage) throws Exception {

        final InputStream documentContent = httpRequestClient.target(requestMessage.getDocumentUri())
                .request()
                .accept(MediaType.APPLICATION_OCTET_STREAM_TYPE)
                .get(InputStream.class);

        switch (requestMessage.getExportSystemId()) {
            case ExportSystem.SHAREPOINT:
                return this.sharePointStore.saveDocument(documentContent, requestMessage.getExportMetadata());
            default:
                throw new DocumentExportException("Unsupported export system");
        }
    }
}
