package org.rha.services.document_generation.export.impl;

import org.rha.services.document_generation.export.IDocumentStore;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;
import java.util.UUID;

@Named("sharepoint-store")
@ApplicationScoped
public class SharePointStore implements IDocumentStore {
    @Inject
    Client httpRequestClient;

    @Override
    public URI saveDocument(InputStream documentContent, Map<String,String> exportData) {
        //TODO: This will make the call to the sharepoint service to upload the new document, and then return the URI.
        return URI.create("http://sharepointurl.com/document/" + UUID.randomUUID().toString());
    }
}
