package pl.kielce.tu.worldyouthday.phone.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class PhoneDetailsResource implements Serializable {

    private Long version;
    private String owner;
    private String description;

    @JsonCreator
    public PhoneDetailsResource(
            @JsonProperty("owner") String owner,
            @JsonProperty("description") String description,
            @JsonProperty("version") Long version) {

        this.owner = owner;
        this.description = description;
        this.version = version;
    }

    private PhoneDetailsResource(Builder builder) {
        version = builder.version;
        owner = builder.owner;
        description = builder.description;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Long getVersion() {
        return version;
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

        PhoneDetailsResource that = (PhoneDetailsResource) o;

        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PhoneDetailsResource{" +
                "version=" + version +
                ", owner='" + owner + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static class Builder {
        private Long version;
        private String owner;
        private String description;

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public Builder withOwner(String owner) {
            this.owner = owner;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public PhoneDetailsResource build() {
            return new PhoneDetailsResource(this);
        }
    }
}
