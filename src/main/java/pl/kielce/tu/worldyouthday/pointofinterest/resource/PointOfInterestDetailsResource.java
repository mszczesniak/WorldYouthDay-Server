package pl.kielce.tu.worldyouthday.pointofinterest.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Łukasz Wesołowski on 25.05.2016.
 */
public class PointOfInterestDetailsResource implements Serializable {
    private String name;
    private String description;

    @JsonCreator
    public PointOfInterestDetailsResource(@JsonProperty("name") String name,
                                          @JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
    }

    public PointOfInterestDetailsResource(Builder builder) {
        name = builder.name;
        description = builder.description;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointOfInterestDetailsResource that = (PointOfInterestDetailsResource) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PointOfInterestDetailsResource{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static class Builder {
        private String name;
        private String description;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public PointOfInterestDetailsResource build() {
            return new PointOfInterestDetailsResource(this);
        }
    }
}
