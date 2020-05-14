package pipeline.dto;

import pipeline.PipelineDeserializer;

import javax.json.bind.annotation.JsonbTypeDeserializer;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonbTypeDeserializer(PipelineDeserializer.class)
public abstract class PipelineStep {

    @NotNull(message = "must not be null!")
    private PipelineStepType pipelineStep;

    public PipelineStep(){}

    protected PipelineStep(final PipelineStepType pipelineStep) {
        this.setPipelineStep(pipelineStep);
    }

    public PipelineStepType getPipelineStep() {
        return pipelineStep;
    }

    public void setPipelineStep(final PipelineStepType pipelineStep) {
        this.pipelineStep = pipelineStep;
    }

    public enum PipelineStepType {
        TEMPLATE,
        CONVERT,
        EXPORT,
    }
}
