package org.exercise;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException exception) {
        String message = exception.getMessage();
        if (message.equals("Incorrect password") || message.equals("Invalid authentication credentials") || message.equals("Invalid or expired token")) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(Response.Status.UNAUTHORIZED + " : " + message).type(MediaType.TEXT_PLAIN).build();
        } else if (message.contains("Email is invalid") || message.contains("Password is invalid")) {
            return Response.status(Response.Status.BAD_REQUEST).entity(Response.Status.BAD_REQUEST + " : " + message).type(MediaType.TEXT_PLAIN).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity(Response.Status.CONFLICT + " : " + message).type(MediaType.TEXT_PLAIN).build();
        }
    }

}
