package org.rha.services.document_generation.v2.export;

import org.rha.services.document_generation.v2.export.dto.ExportDocumentRequestMessage;
import org.rha.services.document_generation.v2.export.dto.ExportMetadata;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

public interface IDocumentStore {
    URI saveDocument(ExportMetadata exportData);
}
