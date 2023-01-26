package org.exercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private static final String LOGIN_PATH = "users/login";
    private static final String USERS_PATH = "users";
    private static ObjectMapper objectMapper = new ObjectMapper();

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
    public void shouldReturnCreatedWhenCorrectPostRequestSent() throws JsonProcessingException {
        //given
        User user = new User("example@example.com","eXample141");
        Entity entity = Entity.json(objectMapper.writeValueAsString(user));

        //when
        Response response = target.path(USERS_PATH).request().post(entity);

        //then
        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    public void shouldReturnUnauthorizedWhenDuplicatePostRequestSent() throws JsonProcessingException {
        //given
        User user = new User("example2@example.com","eXample142");
        Entity entity = Entity.json(objectMapper.writeValueAsString(user));
        target.path(USERS_PATH).request().post(entity);

        //when
        Response response = target.path(USERS_PATH).request().post(entity);

        //then
        Assertions.assertEquals(409, response.getStatus());
    }

    @Test
    public void shouldReturnOKWhenUserCreatedAndLoggedIn() throws JsonProcessingException {
        //given
        User user = new User("example3@example.com","eXample143");
        Entity entity = Entity.json(objectMapper.writeValueAsString(user));
        target.path(USERS_PATH).request().post(entity);

        //when
        Response response = target.path(LOGIN_PATH).request().post(entity);

        //then
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    public void shouldReturnUnauthorizedWhenUserDoesntExist() throws JsonProcessingException {
        //given
        User user = new User("example4@example.com","eXample144");
        Entity entity = Entity.json(objectMapper.writeValueAsString(user));

        //when
        Response response = target.path(LOGIN_PATH).request().post(entity);

        //then
        Assertions.assertEquals(401, response.getStatus());
    }

    @Test
    public void shouldReturnUnauthorizedWhenIncorrectPasswordSupplied() throws JsonProcessingException {
        //given
        User user = new User("example5@example.com","eXample145");
        Entity entity = Entity.json(objectMapper.writeValueAsString(user));
        target.path(USERS_PATH).request().post(entity);
        user = new User("example5@example.com","eXample144");
        entity = Entity.json(objectMapper.writeValueAsString(user));

        //when
        Response response = target.path(LOGIN_PATH).request().post(entity);

        //then
        Assertions.assertEquals(401, response.getStatus());
    }

}
