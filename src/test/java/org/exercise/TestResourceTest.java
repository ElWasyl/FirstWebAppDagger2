package org.exercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import org.flywaydb.core.Flyway;
import org.glassfish.grizzly.http.server.HttpServer;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import javax.sql.DataSource;

class TestResourceTest {
    private static HttpServer SERVER;
    private static WebTarget TARGET;
    private static final String LOGIN_PATH = "users/login";
    private static final String USERS_PATH = "users";
    private static final String TESTRESOURCE_PATH = "testresource";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @BeforeAll
    static void setUp() {
        SERVER = Main.startServer();
        Client client = ClientBuilder.newClient();
        TARGET = client.target("http://localhost:8187/");
        DataSource dataSource = createDataSource();
        Flyway flyway = Flyway.configure().dataSource(dataSource).locations("classpath:db/migration").load();
        flyway.migrate();
    }

    @AfterAll
    static void tearDown() {
        SERVER.shutdown();
    }

    private static DataSource createDataSource() {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=0");
        jdbcDataSource.setUser("sa");
        jdbcDataSource.setPassword("");
        return jdbcDataSource;
    }

    @Test
    void shouldReturnOKWhenCorrectTokenSubmitted() throws JsonProcessingException {
        //given
        User user = new User.UserBuilder("example@example.com","eXample141").build();
        Entity entity = Entity.json(OBJECT_MAPPER.writeValueAsString(user));
        TARGET.path(USERS_PATH).request().post(entity);
        Response response = TARGET.path(LOGIN_PATH).request().post(entity);
        String token = response.readEntity(String.class);

        //when
        response = TARGET.path(TESTRESOURCE_PATH).request().header("Authorization", "Bearer " + token).get();

        //then
        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void shouldReturnUnauthorizedWhenIncorrectTokenSubmitted() throws JsonProcessingException {
        //given
        User user = new User.UserBuilder("example@example.com","eXample141").build();
        Entity entity = Entity.json(OBJECT_MAPPER.writeValueAsString(user));
        TARGET.path(USERS_PATH).request().post(entity);
        TARGET.path(LOGIN_PATH).request().post(entity);

        //when
        Response response = TARGET.path(TESTRESOURCE_PATH).request().header("Authorization", "Bearer whateverer").get();

        //then
        Assertions.assertEquals(401, response.getStatus());
    }

}
