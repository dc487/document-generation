package org.rha.services.document_generation.v2.retention;

import org.rha.services.document_generation.v2.db.DocumentHelper;
import org.rha.services.document_generation.v2.db.VersionHelper;

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
