package org.rha.services.document_generation.v2.export;

import org.rha.services.document_generation.v2.export.dto.ExportMetadata;

import java.net.URI;

public interface IDocumentStore {
    URI saveDocument(ExportMetadata exportData);
}
