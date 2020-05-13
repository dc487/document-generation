package org.rha.services.document_generation.versioning.db.dto;

import org.rha.services.document_generation.versioning.dto.CreateVersionRequest;
import pipeline.dto.PipelineStep;
import pipeline.dto.TemplatePipelineStep;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@NamedQueries(
        {
                @NamedQuery(
                        name = ChildDocument.FIND_ALL_DOCS_FOR_VERSION_QUERY,
                        query = "from ChildDocument where " + Version.COLUMN_ID + " = :" + ChildDocument.VERSION_ID_PARAM
                ),
                @NamedQuery(
                        name = ChildDocument.UPDATE_DOCUMENT_URI_QUERY,
                        query = "UPDATE ChildDocument SET " + ChildDocument.COLUMN_URI + " = :" + ChildDocument.DOCUMENT_URI_PARAM + " WHERE " + ChildDocument.COLUMN_TEMPLATE_NAME + " = :" + ChildDocument.TEMPLATE_NAME_PARAM + " AND " + Version.COLUMN_ID + " = :" + ChildDocument.VERSION_ID_PARAM
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
    public static final String TEMPLATE_NAME_PARAM = "templateName";
    public static final String TABLE_NAME = "child_document";
    public static final String COLUMN_ID = "child_document_id";
    public static final String COLUMN_TEMPLATE_NAME = "template_name";
    public static final String COLUMN_URI = "child_document_uri";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = COLUMN_ID)
    private Long childDocumentId;

    @Column(name = COLUMN_TEMPLATE_NAME)
    @NotNull
    private String templateName;

    @Column(name = COLUMN_URI)
    private String childDocumentUri;

    @ManyToOne
    @JoinColumn(name = Version.COLUMN_ID)
    private Version version;

    public ChildDocument() {

    }

    public ChildDocument(String templateName, Version version) {
        this.templateName = templateName;
        this.version = version;
    }

    /**
     * Generates a list of ChildDocument dto from a 'create version' request received on the versioning endpoint
     * @param createVersionRequest the versioning request
     * @param version the version to link the child documents to
     * @return a list containing the child document objects
     */
    public static List<ChildDocument> fromRequest(CreateVersionRequest createVersionRequest, Version version) {
        List<ChildDocument> childDocuments = new ArrayList<>();

        // Create a new child document for each templating step in the request
        for (Map.Entry<String, List<PipelineStep>> entry : createVersionRequest.getProcessingPipelines().entrySet()) {
            for (PipelineStep pipelineStep : entry.getValue()) {
                if (pipelineStep.getPipelineStep().equals(PipelineStep.PipelineStepType.TEMPLATE)) {
                    // Cast to impl type
                    TemplatePipelineStep templatePipelineStep = (TemplatePipelineStep) pipelineStep;

                    // Create new child document with the detail
                    childDocuments.add(new ChildDocument(templatePipelineStep.getTemplateName(), version));
                }
            }
        }
        return childDocuments;
    }

    public Long getChildDocumentId() {
        return childDocumentId;
    }

    public void setChildDocumentId(Long childDocumentId) {
        this.childDocumentId = childDocumentId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
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
