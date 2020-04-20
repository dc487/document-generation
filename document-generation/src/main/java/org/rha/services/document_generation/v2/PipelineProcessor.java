package org.rha.services.document_generation.v2;

import org.rha.services.document_generation.v2.dto.PipelineStep;

import java.net.URI;
import java.util.List;

public interface PipelineProcessor {
    void performNextPipelineStep(final List<PipelineStep> pipelineSteps, final URI currentDocumentUri);
}
