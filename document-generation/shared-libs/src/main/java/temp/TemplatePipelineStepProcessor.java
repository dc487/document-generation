package temp;

import pipeline.dto.PipelineStep;
import pipeline.dto.TemplatePipelineStep;

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
