package org.rha.services.document_generation.versioning.db;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.rha.services.document_generation.versioning.db.dto.Version;
import org.rha.services.document_generation.versioning.dto.CreateVersionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class VersionHelper {
    Logger logger = LoggerFactory.getLogger(VersionHelper.class);

    /**
     * Saves a new version to the database
     * @param version the version to save
     * @return the ID of the saved version
     */
    public Version saveVersion(Version version) {
        // Get session and begin transaction
        Session session = DBHelper.getSession();
        Transaction transaction = session.beginTransaction();

        // Persist the new version
        session.persist(version);

        // Commit and close
        transaction.commit();
        session.close();

        logger.info("Saved new version with ID " + version.getVersionId() + " to database!");

        return version;
    }

    /**
     * Extracts the version information from a 'create version' request received on the versioning endpoint and saves a
     * new version to the database
     * @param createVersionRequest the request received from the versioning service
     * @return the ID of the saved version
     */
    public Version saveVersion(CreateVersionRequest createVersionRequest) {
        return saveVersion(Version.fromRequest(createVersionRequest));
    }

    /**
     * Retrieves the version with the specified ID from the database
     * @param versionId the ID of the version to get
     * @return an instance of the Version model representing the retrieved version, null if no version found with ID
     */
    @SuppressWarnings("unchecked")
    public Version getVersionById(Long versionId) {
        // Get session and begin transaction
        Session session = DBHelper.getSession();
        Transaction transaction = session.beginTransaction();

        Version version = session.get(Version.class, versionId);

        // Commit and close
        transaction.commit();
        session.close();

        return version;
    }

    /**
     * Retrieves all versions that match the specified source system ID, document type and document URN from the database
     * @param sourceSystemId
     * @param documentType
     * @param documentUrn
     * @return a list containing the matched version objects
     */
    @SuppressWarnings("unchecked")
    public List<Version> getAllVersionsMatching(String sourceSystemId, String documentType, String documentUrn) {
        // Get session and begin transaction
        Session session = DBHelper.getSession();
        Transaction transaction = session.beginTransaction();

        // Query for all versions matching the params
        Query query = session.getNamedQuery(Version.FIND_ALL_VERSIONS_MATCHING_QUERY);
        query.setParameter(Version.SOURCE_SYSTEM_ID_PARAM, sourceSystemId);
        query.setParameter(Version.DOCUMENT_TYPE_PARAM, documentType);
        query.setParameter(Version.DOCUMENT_URN_PARAM, documentUrn);
        List<Version> retrievedVersions = (List<Version>) query.getResultList();

        // Commit and close
        transaction.commit();
        session.close();

        logger.info("Found " + retrievedVersions.size() + " matching versions");

        return retrievedVersions;
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
