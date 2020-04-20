package org.rha.services.document_generation.v2.export;

import org.rha.services.document_generation.v2.export.dto.ExportMetadata;

import javax.enterprise.context.ApplicationScoped;
import java.net.URI;

@ApplicationScoped
public class SharePointStore implements IDocumentStore {
    @Override
    public URI saveDocument(ExportMetadata exportData) {
        return null;
    }
}
