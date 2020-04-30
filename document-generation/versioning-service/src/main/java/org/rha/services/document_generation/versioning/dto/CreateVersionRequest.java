package org.rha.services.document_generation.versioning.dto;


import org.rha.services.document_generation.versioning.pipeline.dto.PipelineStep;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CreateVersionRequest {
    private VersionRequest versioning;
    private Map<String, List<PipelineStep>> processingPipelines;

    public VersionRequest getVersioning() {
        return versioning;
    }

    public void setVersioning(VersionRequest versioning) {
        this.versioning = versioning;
    }

    public Map<String, List<PipelineStep>> getProcessingPipelines() {
        return processingPipelines;
    }

    public void setProcessingPipelines(final Map<String, List<PipelineStep>> processingPipelines) {
        this.processingPipelines = Objects.requireNonNullElse(processingPipelines, Collections.emptyMap());
    }
}
