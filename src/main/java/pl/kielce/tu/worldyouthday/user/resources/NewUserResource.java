package pl.kielce.tu.worldyouthday.user.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class NewUserResource implements Serializable {

    private final String cityId;
    private final String login;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String email;

    @JsonCreator
    public NewUserResource(@JsonProperty("cityId") String cityId,
                           @JsonProperty("login") String login,
                           @JsonProperty("firstName") String firstName,
                           @JsonProperty("lastName") String lastName,
                           @JsonProperty("address") String address,
                           @JsonProperty("email") String email) {

        this.cityId = cityId;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
    }

    private NewUserResource(Builder builder) {
        this.cityId = builder.cityId;
        this.login = builder.login;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.address = builder.address;
        this.email = builder.email;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getCityId() {
        return cityId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewUserResource that = (NewUserResource) o;

        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        return !(email != null ? !email.equals(that.email) : that.email != null);

    }

    @Override
    public int hashCode() {
        int result = cityId != null ? cityId.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewUserResource{" +
                "cityId='" + cityId + '\'' +
                ", login='" + login + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static class Builder {

        private String cityId;
        private String login;
        private String firstName;
        private String lastName;
        private String address;
        private String email;

        public Builder withCityId(String cityId) {
            this.cityId = cityId;
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

        public NewUserResource build() {
            return new NewUserResource(this);
        }

    }

}
