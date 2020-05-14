package pipeline.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;

public class TemplatePipelineStep extends PipelineStep {

    @NotBlank(message = "must not be empty or null!")
    private String templateSystemId;

    @NotBlank(message = "must not be empty or null!")
    private String templateName;

    @NotNull(message = "must not be null!")
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
