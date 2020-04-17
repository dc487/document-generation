package org.rha.services.document_generation.db.dto;

import javax.persistence.*;
import java.time.LocalDate;

@NamedQueries(
    {
        @NamedQuery(
            name = Document.FIND_BY_ID_QUERY,
            query = "from Document where " + Document.COLUMN_ID + " = :" + Document.FIND_BY_ID_QUERY_PARAM
        ),
        @NamedQuery(
                name = Document.FIND_BY_FILE_NAME_QUERY,
                query = "from Document where " + Document.COLUMN_FILE_NAME + " = :" + Document.FIND_BY_FILE_NAME_QUERY_PARAM
        ),
        @NamedQuery(
                name = Document.DELETE_WITH_DELETION_DATE_BEFORE_QUERY,
                query = "delete Document where " + Document.COLUMN_DELETION_DATE + " <= :" + Document.DELETE_WITH_DELETION_DATE_BEFORE_QUERY_PARAM
        )
    }
)

@Entity
@Table(name = Document.TABLE_NAME)
public class Document {

    public static final String FIND_BY_ID_QUERY = "findDocumentById";
    public static final String FIND_BY_ID_QUERY_PARAM = "id";
    public static final String FIND_BY_FILE_NAME_QUERY = "findDocumentByFileName";
    public static final String FIND_BY_FILE_NAME_QUERY_PARAM = "fileName";
    public static final String DELETE_WITH_DELETION_DATE_BEFORE_QUERY = "deleteAllDocumentsWithDeletionDateOlderThan";
    public static final String DELETE_WITH_DELETION_DATE_BEFORE_QUERY_PARAM = "date";

    public static final String TABLE_NAME = "templated_document";
    public static final String COLUMN_ID = "document_id";
    public static final String COLUMN_FILE_NAME = "file_name";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_SECURITY_LABEL = "security_label";
    public static final String COLUMN_DELETION_DATE = "deletion_date";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = COLUMN_ID)
    private int id;

    @Column(name = COLUMN_FILE_NAME, nullable = false)
    private String fileName;

    @Lob
    @Column(name = COLUMN_CONTENT, nullable = false)
    private byte[] content;

    @Column(name = COLUMN_SECURITY_LABEL, nullable = false)
    private String securityLabel;

    @Column(name = COLUMN_DELETION_DATE, nullable = false)
    private LocalDate deletionDate;

    public Document() {

    }

    public Document(String fileName, byte[] content, String securityLabel, LocalDate deletionDate) {
        this.fileName = fileName;
        this.content = content;
        this.securityLabel = securityLabel;
        this.deletionDate = deletionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
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
