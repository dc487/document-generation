package org.rha.services.document_generation.db.dto;

import javax.persistence.*;

@NamedQueries(
    {
        @NamedQuery(
            name = "getDocumentById",
            query = "from Document d where d.id = :id"
        )
    }
)

@Entity
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String fileName;
    private byte[] content;
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
