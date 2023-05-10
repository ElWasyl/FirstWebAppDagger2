package org.exercise;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/testresource")
public class TestResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String testing(@HeaderParam("Authorization") String authHeader) {
        if ((authHeader != null) && (authHeader.startsWith("Bearer "))) {
            String token = authHeader.substring("Bearer ".length());
            if (UserDAO.checkToken(token)) {
                StringBuilder stringBuilder = new StringBuilder(token);
                return stringBuilder.reverse().toString();
            }
        }
        throw new ValidationException("Invalid or expired token");
    }

}
