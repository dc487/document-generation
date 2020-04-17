package org.rha.services.document_generation.db.dto;

import javax.persistence.*;
import java.time.LocalDate;

@NamedQueries(
        {
                @NamedQuery(
                        name = Version.FIND_QUERY,
                        query = "from Version where " + Version.COLUMN_ID + " = :" + Version.FIND_QUERY_PARAM
                ),
                @NamedQuery(
                        name = Version.DELETE_WITH_DELETION_DATE_BEFORE_QUERY,
                        query = "DELETE from Version where " + Version.COLUMN_DELETION_DATE + " <= :" + Version.DELETE_WITH_DELETION_DATE_BEFORE_QUERY_PARAM
                )
        }
)

@Entity
@Table(name = Version.TABLE_NAME)
public class Version {

    public static final String FIND_QUERY = "findVersionById";
    public static final String FIND_QUERY_PARAM = "id";
    public static final String DELETE_WITH_DELETION_DATE_BEFORE_QUERY = "deleteAllVersionsWithDeletionDateOlderThan";
    public static final String DELETE_WITH_DELETION_DATE_BEFORE_QUERY_PARAM = "date";

    public static final String TABLE_NAME = "version";
    public static final String COLUMN_ID = "version_id";
    public static final String COLUMN_SOURCE_SYSTEM_ID = "source_system_id";
    public static final String COLUMN_DOCUMENT_TYPE = "document_type";
    public static final String COLUMN_DOCUMENT_URN = "document_urn";
    public static final String COLUMN_DOCUMENT_CONTENT_URI = "document_content_uri";
    public static final String COLUMN_SECURITY_LABEL = "security_label";
    public static final String COLUMN_DELETION_DATE = "deletion_date";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = COLUMN_ID)
    private int versionId;

    @Column(name = COLUMN_SOURCE_SYSTEM_ID, nullable = false)
    private String sourceSystemId;

    @Column(name = COLUMN_DOCUMENT_TYPE, nullable = false)
    private String documentType;

    @Column(name = COLUMN_DOCUMENT_URN, nullable = false)
    private String documentUrn;

    @Column(name = COLUMN_DOCUMENT_CONTENT_URI, nullable = false)
    private String documentContentUri;

    @Column(name = COLUMN_SECURITY_LABEL, nullable = false)
    private String securityLabel;

    @Column(name = COLUMN_DELETION_DATE, nullable = false)
    private LocalDate deletionDate;

    public Version() {

    }

    public Version(String sourceSystemId, String documentType, String documentUrn, String documentContentUri) {
        this.sourceSystemId = sourceSystemId;
        this.documentType = documentType;
        this.documentUrn = documentUrn;
        this.documentContentUri = documentContentUri;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
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

    public String getDocumentContentUri() {
        return documentContentUri;
    }

    public void setDocumentContentUri(String documentContentUri) {
        this.documentContentUri = documentContentUri;
    }

    public String getSecurityLabel() {
        return securityLabel;
    }

    public void setSecurityLabel(String securityLabel) {
        this.securityLabel = securityLabel;
    }

    public LocalDate getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(LocalDate deletionDate) {
        this.deletionDate = deletionDate;
    }
}
