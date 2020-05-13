package templating;

public class DeleteTemplatedDocumentMessage {
    private Long templatedDocumentId;

    public DeleteTemplatedDocumentMessage() {

    }

    public Long getTemplatedDocumentId() {
        return templatedDocumentId;
    }

    public void setTemplatedDocumentId(Long templatedDocumentId) {
        this.templatedDocumentId = templatedDocumentId;
    }
}
