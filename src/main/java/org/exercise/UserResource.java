package org.exercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final EmailValidator EMAIL_VALIDATOR = new EmailValidator();
    private static final PasswordValidator PASSWORD_VALIDATOR = new PasswordValidator();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String json) throws JsonProcessingException {
        User.UserBuilder userBuilder = OBJECT_MAPPER.readValue(json, User.UserBuilder.class);
        User user = userBuilder.build();
        EMAIL_VALIDATOR.isValid(user.getEmail());
        PASSWORD_VALIDATOR.isValid(user.getPassword());
        UserDAO.addUser(user);
        return Response.status(201).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String json) throws JsonProcessingException {
        Credentials credentials = OBJECT_MAPPER.readValue(json, Credentials.class);
        User user = UserDAO.getUser(credentials.getEmail());
        if (user == null) {
            throw new UserDAOException("Invalid authentication credentials", Response.Status.UNAUTHORIZED);
        }
        if (!user.getPassword().equals(credentials.getPassword())) {
            throw new ValidationException("Incorrect password", Response.Status.UNAUTHORIZED);
        }
        String token = TokenGenerator.generateToken();
        return Response.ok(token).build();
    }

}
