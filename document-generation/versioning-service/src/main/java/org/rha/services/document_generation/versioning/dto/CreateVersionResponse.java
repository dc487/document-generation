package org.rha.services.document_generation.versioning.dto;

public class CreateVersionResponse {
    private ResponseVersion version;

    public ResponseVersion getVersion() {
        return version;
    }

    public void setVersion(final ResponseVersion version) {
        this.version = version;
    }
}
