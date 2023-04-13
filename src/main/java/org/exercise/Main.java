package org.exercise;

import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {
    public static final String BASE_URI = "http://localhost:8187/";

    public static void main(String[] args) {
        HttpServer server = startServer();
    }

    public static HttpServer startServer() {
        DBInitializer.DBInit();
        ResourceConfig rc = new ResourceConfig().packages("org.exercise");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

}
