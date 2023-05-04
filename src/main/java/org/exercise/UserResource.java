package org.exercise;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Set;

@Path("/users")
public class UserResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(@Valid User.UserBuilder userBuilder) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User.UserBuilder>> violations = validator.validate(userBuilder);
        if(!violations.isEmpty()) {
            StringBuilder errorMessage = CheckViolations(violations);
            throw new ValidationException(errorMessage.toString());
        }
        User user = userBuilder.build();
        UserDAO.addUser(user);
        return Response.status(201).build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@Valid User.UserBuilder userBuilder) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User.UserBuilder>> violations = validator.validate(userBuilder);
        if(!violations.isEmpty()) {
            StringBuilder errorMessage = CheckViolations(violations);
            throw new ValidationException(errorMessage.toString());
        }
        User user = userBuilder.build();
        User existingUser = UserDAO.getUser(user.getEmail());
        if (existingUser == null) {
            throw new ValidationException("Invalid authentication credentials");
        }
        if (!user.getPassword().equals(existingUser.getPassword())) {
            throw new ValidationException("Incorrect password");
        }
        String token = TokenGenerator.generateToken();
        UserDAO.registerToken(user, token);
        return Response.ok(token).build();
    }

    private StringBuilder CheckViolations( Set<ConstraintViolation<User.UserBuilder>> violations) {
        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<User.UserBuilder> violation : violations) {
            errorMessage.append(violation.getMessage()).append(", ");
        }
        errorMessage.setLength(errorMessage.length() - 2);
        return errorMessage;
    }

}
