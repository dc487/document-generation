package org.rha.services.document_generation.v2.export.impl;

import org.rha.services.document_generation.v2.export.IDocumentStore;
import org.rha.services.document_generation.v2.export.dto.ExportMetadata;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.net.URI;

@Named("sharepoint-store")
@ApplicationScoped
public class SharePointStore implements IDocumentStore {
    @Override
    public URI saveDocument(ExportMetadata exportData) {
        return null;
    }
}
