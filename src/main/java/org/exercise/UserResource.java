package org.exercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {
    static ObjectMapper mapper = new ObjectMapper();

    public UserResource() {
    }

    @POST
    @Consumes({"application/json"})
    public Response createUser(String json) {
        try {
            User user = (User)mapper.readValue(json, User.class);
            if (!EmailValidator.isValid(user.getEmail())) {
                throw new UserDAOException("Email is invalid.");
            }

            if (!PasswordValidator.isValid(user.getPassword())) {
                throw new UserDAOException("Password is invalid. It can't be empty, and it has to contain at least at least three characters long, and contain at least one capital letter, at least one small letter and at least one number");
            }

            UserDAO.addUser(user);
        } catch (UserDAOException var3) {
            return Response.status(400).entity(var3.getMessage()).build();
        } catch (Exception var4) {
            return Response.status(400).build();
        }

        return Response.status(201).build();
    }

    @POST
    @Path("/login")
    @Consumes({"application/json"})
    public Response login(String json) {
        try {
            Credentials credentials = (Credentials)mapper.readValue(json, Credentials.class);
            User user = UserDAO.getUser(credentials.getEmail());
            if (user == null) {
                throw new UserDAOException("User doesn't exist");
            }

            if (!user.getPassword().equals(credentials.getPassword())) {
                throw new UserDAOException("Incorrect password");
            }
        } catch (UserDAOException var4) {
            return Response.status(401).entity(var4.getMessage()).build();
        } catch (JsonProcessingException var5) {
            return Response.status(401).build();
        }

        return Response.status(200).build();
    }
}
