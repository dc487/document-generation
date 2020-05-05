package pipeline.dto;

import pipeline.PipelineDeserializer;

import javax.json.bind.annotation.JsonbTypeDeserializer;

@JsonbTypeDeserializer(PipelineDeserializer.class)
public abstract class PipelineStep {
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
