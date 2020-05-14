package pipeline.dto;

import javax.validation.constraints.NotBlank;

public class ConvertPipelineStep extends PipelineStep {

    @NotBlank(message = "must not be empty or null!")
    private String fromFormat;

    @NotBlank(message = "must not be empty or null!")
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
