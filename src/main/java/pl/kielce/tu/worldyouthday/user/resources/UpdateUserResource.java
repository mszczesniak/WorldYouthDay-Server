package pl.kielce.tu.worldyouthday.user.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class UpdateUserResource implements Serializable {

    private final String firstName;
    private final String lastName;
    private final String address;

    @JsonCreator
    public UpdateUserResource(@JsonProperty("firstName") String firstName,
                              @JsonProperty("lastName") String lastName,
                              @JsonProperty("address") String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    private UpdateUserResource(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.address = builder.address;
    }

    public static Builder newBuilder() {
        return new Builder();
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateUserResource that = (UpdateUserResource) o;

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        return !(address != null ? !address.equals(that.address) : that.address != null);

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdateUserResource{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static class Builder {

        private String firstName;
        private String lastName;
        private String address;

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

        public UpdateUserResource build() {
            return new UpdateUserResource(this);
        }

    }

}
