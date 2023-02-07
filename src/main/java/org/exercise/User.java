package org.exercise;

import com.fasterxml.jackson.annotation.JsonProperty;

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

        @JsonProperty
        private String email;
        @JsonProperty
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
