package org.rha.services.document_generation.export;

import java.io.InputStream;
import java.net.URI;
import java.util.Map;

public interface IDocumentStore {
    URI saveDocument(InputStream documentContent, Map<String,String> exportData);
}
