package pl.kielce.tu.worldyouthday.security.token.json.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.kielce.tu.worldyouthday.user.resources.UserResource;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class AuthenticationResponse implements Serializable {

    private final String token;
    private final UserResource loggedUser;

    @JsonCreator
    public AuthenticationResponse(@JsonProperty(value = "token") String token,
                                  @JsonProperty(value = "loggedUser") UserResource loggedUser) {
        this.token = token;
        this.loggedUser = loggedUser;
    }

    private AuthenticationResponse(Builder builder) {
        this.token = builder.token;
        this.loggedUser = builder.loggedUser;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getToken() {
        return token;
    }

    public UserResource getLoggedUser() {
        return loggedUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthenticationResponse that = (AuthenticationResponse) o;

        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        return !(loggedUser != null ? !loggedUser.equals(that.loggedUser) : that.loggedUser != null);

    }

    @Override
    public int hashCode() {
        int result = token != null ? token.hashCode() : 0;
        result = 31 * result + (loggedUser != null ? loggedUser.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "token='" + token + '\'' +
                ", loggedUser=" + loggedUser +
                '}';
    }

    public static class Builder {
        private String token;
        private UserResource loggedUser;

        public Builder withToken(String token) {
            this.token = token;
            return this;
        }

        public Builder withLoggedUser(UserResource loggedUser) {
            this.loggedUser = loggedUser;
            return this;
        }

        public AuthenticationResponse build() {
            return new AuthenticationResponse(this);
        }
    }

}
