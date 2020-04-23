package org.rha.services.document_generation.v2.db.dto;

import javax.persistence.*;

@NamedQueries(
        {
                @NamedQuery(
                        name = ChildDocument.FIND_ALL_DOCS_FOR_VERSION_QUERY,
                        query = "from ChildDocument where " + Version.COLUMN_ID + " = :" + ChildDocument.VERSION_ID_PARAM
                ),
                @NamedQuery(
                        name = ChildDocument.UPDATE_DOCUMENT_URI_QUERY,
                        query = "UPDATE ChildDocument SET " + ChildDocument.COLUMN_URI + " = :" + ChildDocument.DOCUMENT_URI_PARAM + " WHERE " + ChildDocument.COLUMN_FILE_TYPE + " = :" + ChildDocument.DOCUMENT_FILE_TYPE_PARAM + " AND " + Version.COLUMN_ID + " = :" + ChildDocument.VERSION_ID_PARAM
                )
        }
)

@Entity
@Table(name = ChildDocument.TABLE_NAME)
public class ChildDocument {

    public static final String FIND_ALL_DOCS_FOR_VERSION_QUERY = "findAllDocumentsForVersion";
    public static final String VERSION_ID_PARAM = "versionId";
    public static final String UPDATE_DOCUMENT_URI_QUERY = "updateDocumentVersionId";
    public static final String DOCUMENT_URI_PARAM = "documentUri";
    public static final String DOCUMENT_FILE_TYPE_PARAM = "documentFileType";
    public static final String TABLE_NAME = "child_document";
    public static final String COLUMN_ID = "child_document_id";
    public static final String COLUMN_FILE_TYPE = "child_document_type";
    public static final String COLUMN_URI = "child_document_uri";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = COLUMN_ID)
    private Long childDocumentId;

    @Column(name = COLUMN_FILE_TYPE)
    private String childDocumentFileType;

    @Column(name = COLUMN_URI)
    private String childDocumentUri;

    @OneToOne
    @JoinColumn(name = Version.COLUMN_ID)
    private Version version;

    public ChildDocument() {

    }

    public ChildDocument(Long childDocumentId, String childDocumentFileType, Version version) {
        this.childDocumentId = childDocumentId;
        this.childDocumentFileType = childDocumentFileType;
        this.version = version;
    }

    public Long getChildDocumentId() {
        return childDocumentId;
    }

    public void setChildDocumentId(Long childDocumentId) {
        this.childDocumentId = childDocumentId;
    }

    public String getChildDocumentFileType() {
        return childDocumentFileType;
    }

    public void setChildDocumentFileType(String childDocumentFileType) {
        this.childDocumentFileType = childDocumentFileType;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public String getChildDocumentUri() {
        return childDocumentUri;
    }

    public void setChildDocumentUri(String childDocumentUri) {
        this.childDocumentUri = childDocumentUri;
    }
}
