package pl.kielce.tu.worldyouthday.user.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.kielce.tu.worldyouthday.user.Role;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement
public class UserResource implements Serializable {

    private final String id;
    private final String cityId;
    private final Role role;
    private final String login;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String email;
    private final Date lastPasswordReset;
    private final Long version;

    @JsonCreator
    public UserResource(@JsonProperty(value = "id") String id,
                        @JsonProperty(value = "cityId") String cityId,
                        @JsonProperty(value = "role") Role role,
                        @JsonProperty(value = "login") String login,
                        @JsonProperty(value = "firstName") String firstName,
                        @JsonProperty(value = "lastName") String lastName,
                        @JsonProperty(value = "address") String address,
                        @JsonProperty(value = "email") String email,
                        @JsonProperty(value = "lastPasswordReset") Date lastPasswordReset,
                        @JsonProperty(value = "version") Long version) {
        this.id = id;
        this.cityId = cityId;
        this.role = role;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.lastPasswordReset = lastPasswordReset;
        this.version = version;
    }

    private UserResource(Builder builder) {
        this.id = builder.id;
        this.cityId = builder.cityId;
        this.role = builder.role;
        this.login = builder.login;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.address = builder.address;
        this.email = builder.email;
        this.lastPasswordReset = builder.lastPasswordReset;
        this.version = builder.version;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getCityId() {
        return cityId;
    }

    public Role getRole() {
        return role;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public Date getLastPasswordReset() {
        return lastPasswordReset;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserResource that = (UserResource) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        if (role != that.role) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (lastPasswordReset != null ? !lastPasswordReset.equals(that.lastPasswordReset) : that.lastPasswordReset != null)
            return false;
        return !(version != null ? !version.equals(that.version) : that.version != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (lastPasswordReset != null ? lastPasswordReset.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserResource{" +
                "id='" + id + '\'' +
                ", cityId='" + cityId + '\'' +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", lastPasswordReset=" + lastPasswordReset +
                ", version=" + version +
                '}';
    }

    public static class Builder {

        private String id;
        private String cityId;
        private Role role;
        private String login;
        private String firstName;
        private String lastName;
        private String address;
        private String email;
        private Date lastPasswordReset;
        private Long version;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withCityId(String cityId) {
            this.cityId = cityId;
            return this;
        }

        public Builder withRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder withLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withLastPasswordReset(Date lastPasswordReset) {
            this.lastPasswordReset = lastPasswordReset;
            return this;
        }

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public UserResource build() {
            return new UserResource(this);
        }

    }

}
