package org.rha.services.document_generation.v2.versioning.dto;

public class CreateVersionResponse {
    private Version version;

    public Version getVersion() {
        return version;
    }

    public void setVersion(final Version version) {
        this.version = version;
    }
}
