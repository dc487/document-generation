package pipeline.dto;

import java.net.URI;

public class TemplatePipelineStep extends PipelineStep {
    private String templateSystemId;
    private URI documentTemplateUri;

    public TemplatePipelineStep() {
        super(PipelineStepType.TEMPLATE);
    }

    public String getTemplateSystemId() {
        return templateSystemId;
    }

    public void setTemplateSystemId(final String templateSystemId) {
        this.templateSystemId = templateSystemId;
    }

    public URI getDocumentTemplateUri() {
        return documentTemplateUri;
    }

    public void setDocumentTemplateUri(final URI documentTemplateUri) {
        this.documentTemplateUri = documentTemplateUri;
    }
}
