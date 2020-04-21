package org.rha.services.document_generation.v2.templating.dto;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TemplateDocumentMessage {
    private URI documentContentUri;
    private String templateName;
    private URI documentTemplateUri;
    private String sourceSystemId;
    private String documentType;
    private String documentUrn;
    private List<PipelineStep> processingPipeline;

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

    public List<PipelineStep> getProcessingPipeline() {
        return processingPipeline;
    }

    public void setProcessingPipeline(final List<PipelineStep> processingPipeline) {
        this.processingPipeline = Objects.requireNonNullElse(processingPipeline, Collections.emptyList());
    }
}
