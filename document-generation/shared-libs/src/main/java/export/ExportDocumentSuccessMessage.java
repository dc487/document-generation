package export;

import java.net.URI;

public class ExportDocumentSuccessMessage {
    private URI exportedFileUri;
    private String exportSystemId;
    private String sourceSystemId;
    private String documentType;
    private String documentUrn;
    private Long versionId;
    private String templateName;

    public ExportDocumentSuccessMessage() {

    }

    public ExportDocumentSuccessMessage(final URI exportedFileUri, final String exportSystemId, final String sourceSystemId, final String documentType, final String documentUrn, final Long versionId, final String templateName) {
        this.exportedFileUri = exportedFileUri;
        this.exportSystemId = exportSystemId;
        this.sourceSystemId = sourceSystemId;
        this.documentType = documentType;
        this.documentUrn = documentUrn;
        this.versionId = versionId;
        this.templateName = templateName;
    }

    public URI getExportedFileUri() {
        return exportedFileUri;
    }

    public void setExportedFileUri(URI exportedFileUri) {
        this.exportedFileUri = exportedFileUri;
    }

    public String getExportSystemId() {
        return exportSystemId;
    }

    public void setExportSystemId(String exportSystemId) {
        this.exportSystemId = exportSystemId;
    }

    public String getSourceSystemId() {
        return sourceSystemId;
    }

    public void setSourceSystemId(String sourceSystemId) {
        this.sourceSystemId = sourceSystemId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentUrn() {
        return documentUrn;
    }

    public void setDocumentUrn(String documentUrn) {
        this.documentUrn = documentUrn;
    }

    public Long getVersionId() {
        return versionId;
    }

    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
