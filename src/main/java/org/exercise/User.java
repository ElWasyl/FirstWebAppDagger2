package org.exercise;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;

public class User {

    private final String email;
    private final String password;

    public User(UserBuilder builder) {
        this.email = builder.email;
        this.password = builder.password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public static class UserBuilder {
        private static final String EMAIL_PATTERN = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{3,10}$";

        @JsonProperty
        @Pattern(regexp = EMAIL_PATTERN, message = "Email is invalid. Please provide a valid email address.")
        private String email;


        @JsonProperty
        @Pattern(regexp = PASSWORD_PATTERN, message = "Password is invalid. It has to contain at least one capital letter, one small letter and one number.")
        private String password;

        public UserBuilder() {
        }

        public UserBuilder(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public User build() {
            return new User(this);
        }

    }

}
