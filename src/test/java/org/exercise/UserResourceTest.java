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
        //given
        Entity entity = Entity.json("{\"email\":\"example@example.com\",\"password\":\"eXample143\"}");

        //when
        Response response = target.path("users").request().post(entity);

        //then
        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    public void shouldReturnUnauthorizedWhenDuplicatePostRequestSent() {
        //given
        Entity entity = Entity.json("{\"email\":\"example2@example.com\",\"password\":\"eXample143\"}");
        target.path("users").request().post(entity);

        //when
        Response response = target.path("users").request().post(entity);

        //then
        Assertions.assertEquals(401, response.getStatus());
    }

    @Test
    public void shouldReturnOKWhenUserCreatedAndLoggedIn() {
        //given
        Entity entity = Entity.json("{\"email\":\"example4@example.com\",\"password\":\"eXample143\"}");
        target.path("users").request().post(entity);

        //when
        Response response = target.path("users/login").request().post(entity);

        //then
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void shouldReturnUnauthorizedWhenUserDoesntExist() {
        //given
        Entity entity = Entity.json("{\"email\":\"example5@example.com\",\"password\":\"eXample143\"}");

        //when
        Response response = target.path("users/login").request().post(entity);

        //then
        Assertions.assertEquals(401, response.getStatus());
    }

    @Test
    public void shouldReturnUnauthorizedWhenIncorrectPasswordSupplied() {
        //given
        Entity entity = Entity.json("{\"email\":\"example6@example.com\",\"password\":\"eXample143\"}");
        target.path("users").request().post(entity);
        entity = Entity.json("{\"email\":\"example6@example.com\",\"password\":\"eXample145\"}");

        //when
        Response response = target.path("users/login").request().post(entity);

        //then
        Assertions.assertEquals(401, response.getStatus());
    }

}
