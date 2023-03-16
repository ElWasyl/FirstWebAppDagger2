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
    private static HttpServer SERVER;
    private static WebTarget TARGET;
    private static final String LOGIN_PATH = "users/login";
    private static final String USERS_PATH = "users";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @BeforeAll
    static void setUp() {
        SERVER = Main.startServer();
        Client client = ClientBuilder.newClient();
        TARGET = client.target("http://localhost:8187/");
    }

    @AfterAll
    static void tearDown() {
        SERVER.shutdown();
    }

    @Test
    void shouldReturnCreatedWhenCorrectPostRequestSent() throws JsonProcessingException {
        //given
        User user = new User.UserBuilder("example@example.com","eXample141").build();
        Entity entity = Entity.json(OBJECT_MAPPER.writeValueAsString(user));

        //when
        Response response = TARGET.path(USERS_PATH).request().post(entity);

        //then
        Assertions.assertEquals(201, response.getStatus());
    }

    @Test
    void shouldReturnUnauthorizedWhenDuplicatePostRequestSent() throws JsonProcessingException {
        //given
        User user = new User.UserBuilder("example2@example.com","eXample142").build();
        Entity entity = Entity.json(OBJECT_MAPPER.writeValueAsString(user));
        TARGET.path(USERS_PATH).request().post(entity);

        //when
        Response response = TARGET.path(USERS_PATH).request().post(entity);

        //then
        Assertions.assertEquals(409, response.getStatus());
    }

    @Test
    void shouldReturnOKWhenUserCreatedAndLoggedIn() throws JsonProcessingException {
        //given
        User user = new User.UserBuilder("example3@example.com","eXample143").build();
        Entity entity = Entity.json(OBJECT_MAPPER.writeValueAsString(user));
        TARGET.path(USERS_PATH).request().post(entity);

        //when
        Response response = TARGET.path(LOGIN_PATH).request().post(entity);

        //then
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void shouldReturnUnauthorizedWhenUserDoesntExist() throws JsonProcessingException {
        //given
        User user = new User.UserBuilder("example4@example.com","eXample144").build();
        Entity entity = Entity.json(OBJECT_MAPPER.writeValueAsString(user));

        //when
        Response response = TARGET.path(LOGIN_PATH).request().post(entity);

        //then
        Assertions.assertEquals(401, response.getStatus());
    }

    @Test
    void shouldReturnUnauthorizedWhenIncorrectPasswordSupplied() throws JsonProcessingException {
        //given
        User user = new User.UserBuilder("example5@example.com","eXample145").build();
        Entity entity = Entity.json(OBJECT_MAPPER.writeValueAsString(user));
        TARGET.path(USERS_PATH).request().post(entity);
        user = new User.UserBuilder("example5@example.com","eXample144").build();
        entity = Entity.json(OBJECT_MAPPER.writeValueAsString(user));

        //when
        Response response = TARGET.path(LOGIN_PATH).request().post(entity);

        //then
        Assertions.assertEquals(401, response.getStatus());
    }

    @Test
    void shouldReturnBadRequestWhenInvalidJSONSupplied() {
        //given
        Entity entity = Entity.json("{\"invalidfield1\": \"john@examle.com\",\"invalidfield2\": \"eXample143\"}");

        //when
        Response response = TARGET.path(USERS_PATH).request().post(entity);

        //then
        Assertions.assertEquals(400, response.getStatus());
    }

}
