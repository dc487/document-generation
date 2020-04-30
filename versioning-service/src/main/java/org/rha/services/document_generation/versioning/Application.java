package org.rha.services.document_generation.versioning;

import javax.enterprise.inject.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class Application {
    @Produces
    public Client provideClient() {
        return ClientBuilder.newClient();
    }
}
