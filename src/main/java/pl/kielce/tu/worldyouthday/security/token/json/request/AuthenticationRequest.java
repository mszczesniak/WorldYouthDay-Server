package pl.kielce.tu.worldyouthday.security.token.json.request;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = 6624726180748515507L;
    private String username;
    private String password;

    public AuthenticationRequest() {
        super();
    }

    @JsonCreator
    public AuthenticationRequest(@JsonProperty(value = "username") String username,
                                 @JsonProperty(value = "password") String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
