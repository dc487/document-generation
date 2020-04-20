package org.rha.services.document_generation.v2.export.impl;

import org.rha.services.document_generation.v2.export.DocumentExportException;
import org.rha.services.document_generation.v2.export.IDocumentExporter;
import org.rha.services.document_generation.v2.export.IDocumentStore;
import org.rha.services.document_generation.v2.export.dto.ExportDocumentRequestMessage;
import org.rha.services.document_generation.v2.export.dto.ExportSystem;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.net.URI;

@ApplicationScoped
public class DocumentExporter implements IDocumentExporter {
    @Inject
    @Named("sharepoint-store")
    IDocumentStore sharePointStore;

    @Override
    public URI saveDocument(ExportDocumentRequestMessage requestMessage) throws Exception {
        switch (requestMessage.getExportSystemId()) {
            case ExportSystem.SHAREPOINT:
                return this.sharePointStore.saveDocument(requestMessage.getExportMetadata());
            default:
                throw new DocumentExportException("Unsupported export system");
        }
    }
}
