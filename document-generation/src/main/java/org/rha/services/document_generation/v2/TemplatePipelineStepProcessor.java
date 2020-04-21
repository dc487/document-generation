package org.rha.services.document_generation.v2;

import org.rha.services.document_generation.v2.dto.PipelineStep;
import org.rha.services.document_generation.v2.dto.TemplatePipelineStep;

import javax.enterprise.context.ApplicationScoped;
import java.net.URI;
import java.util.List;

@ApplicationScoped
class TemplatePipelineStepProcessor {
    void performTemplatePipelineStep(final TemplatePipelineStep templatePipelineStep,
                                     final URI currentDocumentUri,
                                     final List<PipelineStep> nextPipelineSteps) {

    }
}
