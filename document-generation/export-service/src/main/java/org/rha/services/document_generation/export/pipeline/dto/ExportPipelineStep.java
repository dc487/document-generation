package org.rha.services.document_generation.export.pipeline.dto;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class ExportPipelineStep extends PipelineStep {
    private String exportSystemId;
    private Map<String, String> exportMetadata;

    public ExportPipelineStep() {
        super(PipelineStepType.EXPORT);
    }

    public String getExportSystemId() {
        return exportSystemId;
    }

    public void setExportSystemId(final String exportSystemId) {
        this.exportSystemId = exportSystemId;
    }

    public Map<String, String> getExportMetadata() {
        return exportMetadata;
    }

    public void setExportMetadata(final Map<String, String> exportMetadata) {
        this.exportMetadata = Objects.requireNonNullElse(exportMetadata, Collections.emptyMap());
    }
}
