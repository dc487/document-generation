package org.rha.services.document_generation.v2.db;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.rha.services.document_generation.v2.db.dto.ChildDocument;
import org.rha.services.document_generation.v2.db.dto.Version;
import org.rha.services.document_generation.v2.versioning.dto.CreateVersionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class ChildDocumentHelper {
    Logger logger = LoggerFactory.getLogger(ChildDocumentHelper.class);

    /**
     * Saves a new child document to the database
     * @param childDocument the child document to save
     * @return the ID of the saved child document
     */
    public ChildDocument saveChildDocument(ChildDocument childDocument) {
        // Get session and begin transaction
        Session session = DBHelper.getSession();
        Transaction transaction = session.beginTransaction();

        // Persist the new child document
        session.persist(childDocument);

        // Commit and close
        transaction.commit();
        session.close();

        logger.info("Saved new child docment with id " + childDocument.getChildDocumentId() + " to database!");

        return childDocument;
    }

    /**
     * Adds new child document entries to the database linked to the specified version for each templating step in a
     * 'create version' request received on the versioning endpoint
     * @param createVersionRequest the versioning request
     * @param version the version to link the child documents to
     * @return a list containing the generated child document objects
     */
    public List<ChildDocument> saveAllChildDocuments(CreateVersionRequest createVersionRequest, Version version) {
        List<ChildDocument> childDocuments = new ArrayList<>();
        for (ChildDocument childDocument : ChildDocument.fromRequest(createVersionRequest, version)) {
            childDocuments.add(saveChildDocument(childDocument));
        }
        return childDocuments;
    }

    /**
     * Retrieves a list of all child documents linked to the specified version from the database
     * @param version the version to match child documents against
     * @return a list containing the child document objects
     */
    @SuppressWarnings("unchecked")
    public List<ChildDocument> getAllDocumentsForVersion(Version version) {
        // Get session and begin transaction
        Session session = DBHelper.getSession();
        Transaction transaction = session.beginTransaction();

        // Query for all documents that reference the specified version
        Query query = session.getNamedQuery(ChildDocument.FIND_ALL_DOCS_FOR_VERSION_QUERY);
        query.setParameter(ChildDocument.VERSION_ID_PARAM, version);
        List<ChildDocument> retrievedDocs = (List<ChildDocument>) query.getResultList();

        // Commit and close
        transaction.commit();
        session.close();

        logger.info("Retrieved all " + retrievedDocs.size() + " child docs for version with ID " + version.getVersionId());
        return retrievedDocs;
    }

    /**
     * Updates the document URI field for all child documents for the specified version and document file type with the specified URI
     * @param version the version to match documents against
     * @param documentFileType the template file type to match documents against
     * @param newUri the URI to update the child document's document URI field to
     * @return count for the number of documents updated
     */
    @SuppressWarnings("unchecked")
    public int updateDocumentUriForVersionAndFileType(Version version, String documentFileType, String newUri) {
        // Get session and begin transaction
        Session session = DBHelper.getSession();
        Transaction transaction = session.beginTransaction();

        // Update query for all documents that reference the specified version
        Query query = session.getNamedQuery(ChildDocument.UPDATE_DOCUMENT_URI_QUERY);
        query.setParameter(ChildDocument.VERSION_ID_PARAM, version);
        query.setParameter(ChildDocument.DOCUMENT_FILE_TYPE_PARAM, documentFileType);
        query.setParameter(ChildDocument.DOCUMENT_URI_PARAM, newUri);
        int updateCount = query.executeUpdate();

        // Commit and close
        transaction.commit();
        session.close();

        return updateCount;
    }
}
