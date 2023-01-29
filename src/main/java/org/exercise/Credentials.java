package org.exercise;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Credentials {

    @JsonProperty
    private String email;
    @JsonProperty
    private String password;

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

}
