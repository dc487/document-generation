package org.rha.services.document_generation.db.dto;

import javax.persistence.*;

@NamedQueries(
    {
        @NamedQuery(
            name = "findDocumentById",
            query = "from Document d where d.id = :id"
        ),
        @NamedQuery(
                name = "findDocumentByName",
                query = "from Document d where d.fileName = :fileName"
        ),
    }
)

@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String fileName;

    @Lob
    @Column(nullable = false)
    private byte[] content;

    @Column(nullable = false)
    private String securityLabel;

    public Document() {

    }

    public Document(String fileName, byte[] content, String securityLabel) {
        this.fileName = fileName;
        this.content = content;
        this.securityLabel = securityLabel;
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
}
