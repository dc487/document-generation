package org.rha.services.document_generation.export;

import org.rha.services.document_generation.export.dto.ExportMetadata;

import java.io.InputStream;
import java.net.URI;

public interface IDocumentStore {
    URI saveDocument(InputStream documentContent, ExportMetadata exportData);
}
