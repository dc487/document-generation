package export;

import java.net.URI;

public class ExportDocumentFailureMessage {
    private String failureReason;
    private URI documentURI;
    private String sourceSystemId;
    private String documentType;
    private String documentUrn;

    public ExportDocumentFailureMessage() {

    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public URI getDocumentURI() {
        return documentURI;
    }

    public void setDocumentURI(URI documentURI) {
        this.documentURI = documentURI;
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
}
