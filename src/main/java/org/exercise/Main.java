package org.exercise;

import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {
    public static final String BASE_URI = "http://localhost:8187/";

    public static void main(String[] args) throws IOException {
        HttpServer server = startServer();
    }

    public static HttpServer startServer() {
        ResourceConfig rc = (new ResourceConfig()).packages(new String[]{"org.exercise"});
        return GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8187/"), rc);
    }

}
