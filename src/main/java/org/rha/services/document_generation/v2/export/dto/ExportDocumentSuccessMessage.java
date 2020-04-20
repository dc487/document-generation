package org.rha.services.document_generation.v2.export.dto;

import java.net.URI;

public class ExportDocumentSuccessMessage {
    private URI exportedFileLocation;

    public ExportDocumentSuccessMessage() {

    }

    public ExportDocumentSuccessMessage(final URI uri) {
        this.exportedFileLocation = uri;
    }

    public URI getExportedFileLocation() {
        return exportedFileLocation;
    }

    public void setExportedFileLocation(URI exportedFileLocation) {
        this.exportedFileLocation = exportedFileLocation;
    }
}
