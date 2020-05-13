package temp;

import pipeline.dto.ConvertPipelineStep;
import pipeline.dto.PipelineStep;

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

        //Do work

        final URI nextDocumentUri = null;
        pipelineProcessor.performNextPipelineStep(nextPipelineSteps, nextDocumentUri);
    }
}