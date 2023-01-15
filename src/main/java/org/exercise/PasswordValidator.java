package org.exercise;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{3,10}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public boolean isValid(String password) throws ValidationException {
        Matcher matcher = pattern.matcher(password);
        if (matcher.matches()) {
            return true;
        } else {
            throw new ValidationException("Password is invalid. It can't be empty, and it has to contain at least at least three characters long, and contain at least one capital letter, at least one small letter and at least one number");
        }
    }

}
