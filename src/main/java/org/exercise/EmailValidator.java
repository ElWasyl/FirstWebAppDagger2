package org.exercise;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";
    private static final Pattern pattern = Pattern.compile("^(.+)@(\\S+)$");

    public static boolean isValid(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
