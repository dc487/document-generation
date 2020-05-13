package pipeline.dto;

import java.net.URI;

public class TemplatePipelineStep extends PipelineStep {
    private String templateSystemId;
    private String templateName;
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

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public URI getDocumentTemplateUri() {
        return documentTemplateUri;
    }

    public void setDocumentTemplateUri(final URI documentTemplateUri) {
        this.documentTemplateUri = documentTemplateUri;
    }
}
