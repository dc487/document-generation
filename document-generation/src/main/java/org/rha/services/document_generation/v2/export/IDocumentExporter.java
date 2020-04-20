package org.rha.services.document_generation.v2.export;

import org.rha.services.document_generation.v2.export.dto.ExportDocumentRequestMessage;

import java.net.URI;

public interface IDocumentExporter {
    URI saveDocument(ExportDocumentRequestMessage requestMessage) throws Exception;
}
