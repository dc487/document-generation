package org.rha.services.document_generation.versioning.pipeline.dto;

public class ConvertPipelineStep extends PipelineStep {
    private String fromFormat;
    private String toFormat;

    public ConvertPipelineStep() {
        super(PipelineStepType.CONVERT);
    }

    public String getFromFormat() {
        return fromFormat;
    }

    public void setFromFormat(final String fromFormat) {
        this.fromFormat = fromFormat;
    }

    public String getToFormat() {
        return toFormat;
    }

    public void setToFormat(final String toFormat) {
        this.toFormat = toFormat;
    }
}
