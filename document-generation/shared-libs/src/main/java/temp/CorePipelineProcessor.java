package temp;

import pipeline.dto.ConvertPipelineStep;
import pipeline.dto.ExportPipelineStep;
import pipeline.dto.PipelineStep;
import pipeline.dto.TemplatePipelineStep;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CorePipelineProcessor implements PipelineProcessor {
    @Inject
    TemplatePipelineStepProcessor templatePipelineStepProcessor;

    @Inject
    ConvertPipelineStepProcessor convertPipelineStepProcessor;

    @Inject
    ExportPipelineStepProcessor exportPipelineStepProcessor;

    @Override
    public void performNextPipelineStep(final List<PipelineStep> pipelineSteps, final URI currentDocumentUri) {
        if (pipelineSteps == null || pipelineSteps.size() == 0)
            return;

        final PipelineStep currentPipelineStep = pipelineSteps.get(0);
        final List<PipelineStep> nextPipelineSteps = pipelineSteps.stream().skip(1).collect(Collectors.toList());

        switch (currentPipelineStep.getPipelineStep()) {
            case TEMPLATE:
                final TemplatePipelineStep templatePipelineStep = (TemplatePipelineStep) currentPipelineStep;
                templatePipelineStepProcessor.performTemplatePipelineStep(
                        templatePipelineStep,
                        currentDocumentUri,
                        nextPipelineSteps
                );
                break;
            case CONVERT:
                final ConvertPipelineStep convertPipelineStep = (ConvertPipelineStep) currentPipelineStep;
                convertPipelineStepProcessor.performConvertPipelineStep(
                        convertPipelineStep,
                        currentDocumentUri,
                        nextPipelineSteps
                );
                break;
            case EXPORT:
                final ExportPipelineStep exportPipelineStep = (ExportPipelineStep) currentPipelineStep;
                exportPipelineStepProcessor.performExportPipelineStep(
                        exportPipelineStep,
                        currentDocumentUri,
                        nextPipelineSteps
                );
                break;
            default:
                throw new UnsupportedOperationException(
                        "Unknown pipeline step provided: '" + currentPipelineStep.getPipelineStep() + "'");
        }
    }
}
