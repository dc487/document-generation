package temp;

import pipeline.dto.PipelineStep;

import java.net.URI;
import java.util.List;

public interface PipelineProcessor {
    void performNextPipelineStep(final List<PipelineStep> pipelineSteps, final URI currentDocumentUri);
}
