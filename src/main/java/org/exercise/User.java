package org.exercise;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    private String email;
    private String password;

    public User(@JsonProperty("email") String email, @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }


}
