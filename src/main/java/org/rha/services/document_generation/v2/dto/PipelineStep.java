package org.rha.services.document_generation.v2.dto;

public abstract class PipelineStep {
    private PipelineStepType pipelineStep;

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
