package org.rha.services.document_generation.versioning.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FindVersionsResponse {
    private List<ResponseVersion> versions;

    public FindVersionsResponse(List<ResponseVersion> versions) {
        this.versions = versions;
    }

    public List<ResponseVersion> getVersions() {
        return versions;
    }

    public void setVersions(final List<ResponseVersion> versions) {
        this.versions = Objects.requireNonNullElse(versions, Collections.emptyList());
    }
}
