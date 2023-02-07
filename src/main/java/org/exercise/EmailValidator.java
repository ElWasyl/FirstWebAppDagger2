package org.exercise;

import jakarta.ws.rs.core.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    private static final String EMAIL_PATTERN = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    public boolean isValid(String email) throws ValidationException {
        Matcher matcher = PATTERN.matcher(email);
        if (matcher.matches()) {
            return true;
        } else {
            throw new ValidationException("Email is invalid.", Response.Status.fromStatusCode(400));
        }
    }

}
