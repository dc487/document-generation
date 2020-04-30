package org.rha.services.document_generation.export;

import org.rha.services.document_generation.export.dto.ExportDocumentRequestMessage;

import java.net.URI;

public interface IDocumentExporter {
    URI saveDocument(ExportDocumentRequestMessage requestMessage) throws Exception;
}
