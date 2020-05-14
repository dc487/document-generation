package templating;

import pipeline.dto.PipelineStep;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TemplateDocumentMessage {

    @NotNull(message = "Must include Document Content URI!")
    private URI documentContentUri;

    @NotBlank(message = "Must not have a blank templateName!")
    private String templateName;

    private URI documentTemplateUri;
    private String sourceSystemId;
    private String documentType;

    @NotBlank(message = "Must include document URN!")
    private String documentUrn;

    private Long versionId;
    private List<PipelineStep> processingPipelines;

    public URI getDocumentContentUri() {
        return documentContentUri;
    }

    public void setDocumentContentUri(final URI documentContentUri) {
        this.documentContentUri = documentContentUri;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(final String templateName) {
        this.templateName = templateName;
    }

    public URI getDocumentTemplateUri() {
        return documentTemplateUri;
    }

    public void setDocumentTemplateUri(final URI documentTemplateUri) {
        this.documentTemplateUri = documentTemplateUri;
    }

    public String getSourceSystemId() {
        return sourceSystemId;
    }

    public void setSourceSystemId(final String sourceSystemId) {
        this.sourceSystemId = sourceSystemId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(final String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentUrn() {
        return documentUrn;
    }

    public void setDocumentUrn(final String documentUrn) {
        this.documentUrn = documentUrn;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public List<PipelineStep> getProcessingPipelines() {
        return processingPipelines;
    }

    public void setProcessingPipelines(final List<PipelineStep> processingPipelines) {
        this.processingPipelines = Objects.requireNonNullElse(processingPipelines, Collections.emptyList());
    }
}
