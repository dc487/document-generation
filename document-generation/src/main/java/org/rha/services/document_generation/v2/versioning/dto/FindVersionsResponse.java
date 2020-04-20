package org.rha.services.document_generation.v2.versioning.dto;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FindVersionsResponse {
    private List<Version> versions;

    public List<Version> getVersions() {
        return versions;
    }

    public void setVersions(final List<Version> versions) {
        this.versions = Objects.requireNonNullElse(versions, Collections.emptyList());
    }
}
