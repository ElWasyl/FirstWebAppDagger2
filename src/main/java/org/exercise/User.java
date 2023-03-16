package org.exercise;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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
        @NotBlank(message = "Email cannot be blank")
        @Pattern(regexp = EMAIL_PATTERN, message = "Email is invalid.")
        private String email;


        @JsonProperty
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 3, max = 10, message = "Password must be between 3 and 10 characters")
        @Pattern(regexp = PASSWORD_PATTERN, message = "Password is invalid. It has to contain at least one capital letter, one small letter and one number")
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
