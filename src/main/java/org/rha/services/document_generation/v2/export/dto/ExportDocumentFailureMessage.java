package org.rha.services.document_generation.v2.export.dto;

public class ExportDocumentFailureMessage {
    private String failureReason;

    public ExportDocumentFailureMessage() {

    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}
