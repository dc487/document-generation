package org.rha.services.document_generation.components.retention;

import org.rha.services.document_generation.db.DocumentHelper;
import org.rha.services.document_generation.db.dto.VersionHelper;

public class RetentionEnforcer {
    private VersionHelper versionHelper;
    private DocumentHelper documentHelper;

    public RetentionEnforcer() {
        this.versionHelper = new VersionHelper();
        this.documentHelper = new DocumentHelper();
    }

    public void deleteItemsPastRetention() {
        versionHelper.deleteExpiredVersions();
        documentHelper.deleteExpiredDocuments();
    }
}
