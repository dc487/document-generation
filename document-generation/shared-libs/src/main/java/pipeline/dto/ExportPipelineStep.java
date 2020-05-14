package pipeline.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class ExportPipelineStep extends PipelineStep {

    @NotBlank(message = "must not be empty or null!")
    private String exportSystemId;

    @NotNull(message = "must not be null!")
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
