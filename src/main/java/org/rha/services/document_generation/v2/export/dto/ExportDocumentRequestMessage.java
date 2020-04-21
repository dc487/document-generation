package org.rha.services.document_generation.v2.export.dto;

import java.net.URI;

public class ExportDocumentRequestMessage {
    private URI documentUri;
    private String exportSystemId;
    private ExportMetadata exportMetadata;
    private String sourceSystemId;
    private String documentType;
    private String documentUrn;

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

    public String getSourceSystemId() {
        return sourceSystemId;
    }

    public void setSourceSystemId(String sourceSystemId) {
        this.sourceSystemId = sourceSystemId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentUrn() {
        return documentUrn;
    }

    public void setDocumentUrn(String documentUrn) {
        this.documentUrn = documentUrn;
    }
}
