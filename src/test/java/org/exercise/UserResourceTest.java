package org.exercise;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserResourceTest {
    private static HttpServer server;
    private static WebTarget target;

    @BeforeAll
    public static void setUp() {
        server = Main.startServer();
        Client c = ClientBuilder.newClient();
        target = c.target("http://localhost:8187/");
    }

    @AfterAll
    public static void tearDown() {
        server.shutdown();
    }

    @Test
    public void shouldReturnCreatedWhenCorrectPostRequestSent() {
        Entity entity = Entity.json("{\"email\":\"example@example.com\",\"password\":\"eXample143\"}");
        Response response = target.path("users").request().post(entity);
        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    public void shouldReturnBadRequestWhenDuplicatePostRequestSent() {
        Entity entity = Entity.json("{\"email\":\"example2@example.com\",\"password\":\"eXample143\"}");
        target.path("users").request().post(entity);
        Response response = target.path("users").request().post(entity);
        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    public void shouldReturnOKWhenUserCreatedAndLoggedIn() {
        Entity entity = Entity.json("{\"email\":\"example4@example.com\",\"password\":\"eXample143\"}");
        target.path("users").request().post(entity);
        Response response = target.path("users/login").request().post(entity);
        Assertions.assertEquals(200, response.getStatus());
    }
}
