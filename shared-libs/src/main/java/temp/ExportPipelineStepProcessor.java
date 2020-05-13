package temp;

import pipeline.dto.ExportPipelineStep;
import pipeline.dto.PipelineStep;

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
        //Do work

        final URI nextDocumentUri = null;
        pipelineProcessor.performNextPipelineStep(nextPipelineSteps, nextDocumentUri);
    }
}