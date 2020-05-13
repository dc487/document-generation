package org.rha.services.document_generation.templating.db;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.rha.services.document_generation.templating.db.dto.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.time.LocalDate;

@ApplicationScoped
public class DocumentHelper {
    Logger logger = LoggerFactory.getLogger(DocumentHelper.class);

    /**
     * Saves a new document to the database
     * @param document the document to save
     * @return the ID of the saved document
     */
    public Long saveDocument(Document document) {
        // Get session and begin transaction
        Session session = DBHelper.getSession();
        Transaction transaction = session.beginTransaction();

        // Persist the new document
        session.persist(document);

        // Commit and close
        transaction.commit();
        session.close();

        logger.info("Saved new document with ID " + document.getId() + " to database!");

        return document.getId();
    }

    /**
     * Retrieves the document with the specified ID from the database
     * @param documentId the ID of the document to get
     * @return an instance of the Document model representing the retrieved document
     */
    @SuppressWarnings("unchecked")
    public Document getDocumentById(int documentId) {
        // Get session and begin transaction
        Session session = DBHelper.getSession();
        Transaction transaction = session.beginTransaction();

        Document document = session.get(Document.class, documentId);

        // Commit and close
        transaction.commit();
        session.close();

        logger.info("Retrieved document with ID " + documentId + " from database!");

        return document;
    }

    /**
     * Deletes all documents that are past their retention date
     * @return an int representing the number of deleted documents
     */
    public int deleteExpiredDocuments() {
        // Get session and begin transaction
        Session session = DBHelper.getSession();
        Transaction transaction = session.beginTransaction();

        // Query for the document
        Query query = session.getNamedQuery(Document.DELETE_WITH_DELETION_DATE_BEFORE_QUERY);
        query.setParameter(Document.DELETE_WITH_DELETION_DATE_BEFORE_QUERY_PARAM, LocalDate.now());
        int deletionCount = query.executeUpdate();

        // Commit and close
        transaction.commit();
        session.close();

        logger.info("Deleted " + deletionCount + " documents past their retention date!");
        return deletionCount;
    }

    /**
     * Deletes the document with the specified ID from the database
     * @param documentId
     */
    public void deleteDocumentWithId(Long documentId) {
        // Get session and begin transaction
        Session session = DBHelper.getSession();
        Transaction transaction = session.beginTransaction();

        Document document = session.get(Document.class, documentId);

        if (document != null) {
            session.delete(document);
            logger.info("Deleted document with ID " + documentId + " from database!");
        }
        else {
            logger.info("Couldn't find document with ID " + documentId);
        }

        // Commit and close
        transaction.commit();
        session.close();
    }
}
