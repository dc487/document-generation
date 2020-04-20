package org.rha.services.document_generation.v2.export.dto;

public class ExportMetadata {
    private String filename;
    private String folder;
    private String tags;

    public ExportMetadata() {

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
