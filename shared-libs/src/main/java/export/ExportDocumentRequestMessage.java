package export;

import java.net.URI;
import java.util.Map;

public class ExportDocumentRequestMessage {
    private URI documentContentUri;
    private String exportSystemId;
    private Map<String,String> exportMetadata;
    private String sourceSystemId;
    private String documentType;
    private String documentUrn;

    public ExportDocumentRequestMessage() {

    }

    public URI getDocumentContentUri() {
        return documentContentUri;
    }

    public void setDocumentContentUri(URI documentContentUri) {
        this.documentContentUri = documentContentUri;
    }

    public String getExportSystemId() {
        return exportSystemId;
    }

    public void setExportSystemId(String exportSystemId) {
        this.exportSystemId = exportSystemId;
    }

    public Map<String,String> getExportMetadata() {
        return exportMetadata;
    }

    public void setExportMetadata(Map<String,String> exportMetadata) {
        this.exportMetadata = exportMetadata;
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
