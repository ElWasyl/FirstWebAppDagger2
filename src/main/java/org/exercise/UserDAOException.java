package org.exercise;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class UserDAOException extends WebApplicationException {
    public UserDAOException(String message, Response.Status status) {
        super(message, status);
    }

}
