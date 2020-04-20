package org.rha.services.document_generation.v2;

import org.rha.services.document_generation.v2.dto.ExportPipelineStep;
import org.rha.services.document_generation.v2.dto.PipelineStep;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URI;
import java.util.List;

@ApplicationScoped
class ExportPipelineStepProcessor {
    @Inject
    PipelineProcessor pipelineProcessor;

    void performExportPipelineStep(final ExportPipelineStep exportPipelineStep,
                                   final URI currentDocumentUri,
                                   final List<PipelineStep> nextPipelineSteps) {
        final URI nextDocumentUri = null;
        pipelineProcessor.performNextPipelineStep(nextPipelineSteps, nextDocumentUri);
    }
}
