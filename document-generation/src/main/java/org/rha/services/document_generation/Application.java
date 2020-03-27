package org.rha.services.document_generation;

import javax.enterprise.inject.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.ext.Provider;

public class Application {
    @Produces
    public Client provideClient() {
        return ClientBuilder.newClient();
    }
}
