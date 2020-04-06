package org.rha.services.document_generation.db;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.rha.services.document_generation.db.dto.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.TypedQuery;

public class DBHelper {

    Logger logger = LoggerFactory.getLogger(DBHelper.class);

    public DBHelper() {
    }

    public int saveDocument(Document document) {
        // Config
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        // Create session
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();

        // Begin transaction
        Transaction transaction = session.beginTransaction();

        // Persist the new document
        session.persist(document);

        // Commit and close
        transaction.commit();
        logger.info("Saved new document with ID " + document.getId() + " to database!");

        factory.close();
        session.close();

        return document.getId();
    }

    @SuppressWarnings("unchecked")
    public Document getDocument(int documentId) {
        // Config
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        // Create session
        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();

        // Begin transaction
        Transaction transaction = session.beginTransaction();

        // Query for the document
        TypedQuery<Document> query = session.getNamedQuery("findDocumentById");
        query.setParameter("id", documentId);
        Document document = query.getSingleResult();

        // Commit and close
        transaction.commit();
        logger.info("Retrieved document with ID " + documentId + " from database!");

        factory.close();
        session.close();

        return document;
    }
}
