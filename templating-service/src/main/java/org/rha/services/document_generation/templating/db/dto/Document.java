package org.rha.services.document_generation.templating.db.dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NamedQueries(
    {
        @NamedQuery(
            name = Document.FIND_BY_ID_QUERY,
            query = "from Document where " + Document.COLUMN_ID + " = :" + Document.FIND_BY_ID_QUERY_PARAM
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
    public static final String DELETE_WITH_DELETION_DATE_BEFORE_QUERY = "deleteAllDocumentsWithDeletionDateOlderThan";
    public static final String DELETE_WITH_DELETION_DATE_BEFORE_QUERY_PARAM = "date";

    public static final String TABLE_NAME = "templated_document";
    public static final String COLUMN_ID = "document_id";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_SECURITY_LABEL = "security_label";
    public static final String COLUMN_DELETION_DATE = "deletion_date";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = COLUMN_ID)
    private Long id;

    @Lob
    @Column(name = COLUMN_CONTENT)
    @NotNull
    private byte[] content;

    @Column(name = COLUMN_SECURITY_LABEL)
    @NotNull
    private String securityLabel;

    @Column(name = COLUMN_DELETION_DATE)
    @NotNull
    private LocalDate deletionDate;

    public Document() {

    }

    public Document(byte[] content, String securityLabel, LocalDate deletionDate) {
        this.content = content;
        this.securityLabel = securityLabel;
        this.deletionDate = deletionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
