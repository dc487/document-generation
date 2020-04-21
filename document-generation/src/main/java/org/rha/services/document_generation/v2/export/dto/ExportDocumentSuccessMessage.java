package org.rha.services.document_generation.v2.export.dto;

import java.net.URI;

public class ExportDocumentSuccessMessage {
    private URI exportedFileUri;
    private String sourceSystemId;
    private String documentType;
    private String documentUrn;

    public ExportDocumentSuccessMessage() {

    }

    public ExportDocumentSuccessMessage(final URI exportedFileUri, String sourceSystemId, String documentType, String documentUrn) {
        this.exportedFileUri = exportedFileUri;
        this.sourceSystemId = sourceSystemId;
        this.documentType = documentType;
        this.documentUrn = documentUrn;
    }

    public URI getExportedFileLocation() {
        return exportedFileUri;
    }

    public void setExportedFileLocation(URI exportedFileLocation) {
        this.exportedFileUri = exportedFileLocation;
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
