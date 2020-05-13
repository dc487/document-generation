package temp;

import pipeline.dto.PipelineStep;
import pipeline.dto.TemplatePipelineStep;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URI;
import java.util.List;

@ApplicationScoped
class TemplatePipelineStepProcessor {
    @Inject
    PipelineProcessor pipelineProcessor;
    void performTemplatePipelineStep(final TemplatePipelineStep templatePipelineStep,
                                     final URI currentDocumentUri,
                                     final List<PipelineStep> nextPipelineSteps) {

        //Do work

        final URI nextDocumentUri = null;
        pipelineProcessor.performNextPipelineStep(nextPipelineSteps, nextDocumentUri);
    }
}
