package org.rha.services.document_generation.v2;

import org.rha.services.document_generation.v2.dto.ConvertPipelineStep;
import org.rha.services.document_generation.v2.dto.PipelineStep;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URI;
import java.util.List;

@ApplicationScoped
public class ConvertPipelineStepProcessor {
    @Inject
    PipelineProcessor pipelineProcessor;

    void performConvertPipelineStep(final ConvertPipelineStep convertPipelineStep,
                                    final URI currentDocumentUri,
                                    final List<PipelineStep> nextPipelineSteps) {
        final URI nextDocumentUri = null;
        pipelineProcessor.performNextPipelineStep(nextPipelineSteps, nextDocumentUri);
    }
}
