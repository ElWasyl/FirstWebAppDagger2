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
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final EmailValidator emailValidator = new EmailValidator();
    private static final PasswordValidator passwordValidator = new PasswordValidator();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String json) {
        try {
            User user = mapper.readValue(json, User.class);
            emailValidator.isValid(user.getEmail());
            passwordValidator.isValid(user.getPassword());
            UserDAO.addUser(user);
        } catch (UserDAOException e) {
            return Response.status(409).build();
        } catch (JsonProcessingException e) {
            return Response.status(400).build();
        } catch (ValidationException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
        return Response.status(201).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String json) {
        try {
            Credentials credentials = mapper.readValue(json, Credentials.class);
            User user = UserDAO.getUser(credentials.getEmail());
            if (user == null) {
                throw new UserDAOException();
            }
            if (!user.getPassword().equals(credentials.getPassword())) {
                throw new ValidationException("Incorrect password");
            }
        } catch (UserDAOException e) {
            return Response.status(401).build();
        } catch (JsonProcessingException e) {
            return Response.status(400).build();
        } catch (ValidationException e) {
            return Response.status(401).entity(e.getMessage()).build();
        }
        String token = TokenGenerator.generateToken();
        return Response.ok(token).build();
    }

}
