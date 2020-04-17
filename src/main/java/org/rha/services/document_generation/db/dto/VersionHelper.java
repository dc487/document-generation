package org.rha.services.document_generation.db.dto;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.rha.services.document_generation.db.DBHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDate;

public class VersionHelper {
    Logger logger = LoggerFactory.getLogger(VersionHelper.class);

    /**
     * Saves a new version to the database
     * @param version the version to save
     * @return the ID of the saved version
     */
    public int saveVersion(Version version) {
        // Get session and begin transaction
        Session session = DBHelper.getSession();
        Transaction transaction = session.beginTransaction();

        // Persist the new document
        session.persist(version);

        // Commit and close
        transaction.commit();
        session.close();

        logger.info("Saved new version with ID " + version.getVersionId() + " to database!");

        return version.getVersionId();
    }

    /**
     * Retrieves the version with the specified ID from the database
     * @param versionId the ID of the version to get
     * @return an instance of the Version model representing the retrieved version
     */
    @SuppressWarnings("unchecked")
    public Version getVersionById(int versionId) {
        // Get session and begin transaction
        Session session = DBHelper.getSession();
        Transaction transaction = session.beginTransaction();

        // Query for the version
        TypedQuery<Version> query = session.getNamedQuery(Version.FIND_QUERY);
        query.setParameter(Version.FIND_QUERY_PARAM, versionId);
        Version document = query.getSingleResult();

        // Commit and close
        transaction.commit();
        session.close();

        logger.info("Retrieved version with ID " + versionId + " from database!");
        return document;
    }

    /**
     * Deletes all versions that are past their retention date
     * @return an int representing the number of deleted versions
     */
    public int deleteExpiredVersions() {
        // Get session and begin transaction
        Session session = DBHelper.getSession();
        Transaction transaction = session.beginTransaction();

        // Query for the document
        Query query = session.getNamedQuery(Version.DELETE_WITH_DELETION_DATE_BEFORE_QUERY);
        query.setParameter(Version.DELETE_WITH_DELETION_DATE_BEFORE_QUERY_PARAM, LocalDate.now());
        int deletionCount = query.executeUpdate();

        // Commit and close
        transaction.commit();
        session.close();

        logger.info("Deleted " + deletionCount + " versions past their retention date!");
        return deletionCount;
    }
}
