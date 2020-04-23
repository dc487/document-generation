package org.rha.services.document_generation.v2.db;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.rha.services.document_generation.v2.db.dto.ChildDocument;
import org.rha.services.document_generation.v2.db.dto.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Query;
import java.util.List;

public class ChildDocumentHelper {
    Logger logger = LoggerFactory.getLogger(ChildDocumentHelper.class);

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

    @SuppressWarnings("unchecked")
    public int updateDocumentUriForVersionAndFileType(Version version, String documentFileType, String newUri) {
        // Get session and begin transaction
        Session session = DBHelper.getSession();
        Transaction transaction = session.beginTransaction();

        // Query for all documents that reference the specified version
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
