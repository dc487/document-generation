package org.rha.services.document_generation.export;

import export.ExportDocumentRequestMessage;

import java.net.URI;

public interface IDocumentExporter {
    URI saveDocument(ExportDocumentRequestMessage requestMessage) throws Exception;
}
