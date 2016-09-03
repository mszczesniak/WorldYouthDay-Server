package pl.kielce.tu.worldyouthday.pointofinterest.category.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Łukasz Wesołowski on 25.05.2016.
 */
public class PointOfInterestCategoryDetailsResource {
    private String name;

    @JsonCreator
    public PointOfInterestCategoryDetailsResource(@JsonProperty("name") String name) {
        this.name = name;
    }

    public PointOfInterestCategoryDetailsResource(Builder builder) {
        name = builder.name;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointOfInterestCategoryDetailsResource that = (PointOfInterestCategoryDetailsResource) o;

        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PointOfInterestCategoryDetailsResource{" +
                "name='" + name + '\'' +
                '}';
    }

    public static class Builder {
        private String name;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public PointOfInterestCategoryDetailsResource build() {
            return new PointOfInterestCategoryDetailsResource(this);
        }
    }
}
