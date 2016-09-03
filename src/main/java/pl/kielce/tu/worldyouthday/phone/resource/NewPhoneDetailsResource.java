package pl.kielce.tu.worldyouthday.phone.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class NewPhoneDetailsResource implements Serializable {

    private String owner;
    private String description;

    @JsonCreator
    public NewPhoneDetailsResource(
            @JsonProperty("owner") String owner,
            @JsonProperty("description") String description) {

        this.owner = owner;
        this.description = description;
    }

    private NewPhoneDetailsResource(Builder builder) {
        owner = builder.owner;
        description = builder.description;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getOwner() {
        return owner;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewPhoneDetailsResource that = (NewPhoneDetailsResource) o;

        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = owner != null ? owner.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PhoneDetailsResource{" +
                "owner='" + owner + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static class Builder {
        private String owner;
        private String description;

        public Builder withOwner(String owner) {
            this.owner = owner;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public NewPhoneDetailsResource build() {
            return new NewPhoneDetailsResource(this);
        }
    }
}
