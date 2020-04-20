package org.rha.services.document_generation.v2.export.dto;

import java.net.URI;

public class ExportDocumentRequestMessage {
    private URI documentUri;
    private String exportSystemId;
    private ExportMetadata exportMetadata;

    public ExportDocumentRequestMessage() {

    }

    public URI getDocumentUri() {
        return documentUri;
    }

    public void setDocumentUri(URI documentUri) {
        this.documentUri = documentUri;
    }

    public String getExportSystemId() {
        return exportSystemId;
    }

    public void setExportSystemId(String exportSystemId) {
        this.exportSystemId = exportSystemId;
    }

    public ExportMetadata getExportMetadata() {
        return exportMetadata;
    }

    public void setExportMetadata(ExportMetadata exportMetadata) {
        this.exportMetadata = exportMetadata;
    }
}
