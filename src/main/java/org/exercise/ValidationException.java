package org.exercise;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class ValidationException extends WebApplicationException {
    public ValidationException(String message, Response.Status status) {
        super(message, status);
    }

}
