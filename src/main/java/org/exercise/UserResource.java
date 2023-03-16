package org.exercise;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid User.UserBuilder userBuilder) {
        User user = userBuilder.build();
        UserDAO.addUser(user);
        return Response.status(201).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@Valid User.UserBuilder userBuilder) {
        User user = userBuilder.build();
        User existingUser = UserDAO.getUser(user.getEmail());
        if (existingUser == null) {
            throw new UserDAOException("Invalid authentication credentials");
        }
        if (!user.getPassword().equals(existingUser.getPassword())) {
            throw new ValidationException("Incorrect password");
        }
        String token = TokenGenerator.generateToken();
        return Response.ok(token).build();
    }

}
