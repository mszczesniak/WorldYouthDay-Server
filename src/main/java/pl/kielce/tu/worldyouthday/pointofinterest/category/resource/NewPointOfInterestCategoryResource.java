package pl.kielce.tu.worldyouthday.pointofinterest.category.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.kielce.tu.worldyouthday.language.Language;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Łukasz Wesołowski on 12.04.2016.
 */

@XmlRootElement
public class NewPointOfInterestCategoryResource {
    private Map<Language, PointOfInterestCategoryDetailsResource> details;

    @JsonCreator
    public NewPointOfInterestCategoryResource(@JsonProperty("details") Map<Language, PointOfInterestCategoryDetailsResource> details) {
        this.details = details;
    }

    public NewPointOfInterestCategoryResource(Builder builder) {
        details = builder.details;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Map<Language, PointOfInterestCategoryDetailsResource> getDetails() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewPointOfInterestCategoryResource that = (NewPointOfInterestCategoryResource) o;

        return details != null ? details.equals(that.details) : that.details == null;

    }

    @Override
    public int hashCode() {
        return details != null ? details.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "NewPointOfInterestCategoryResource{" +
                "details=" + details +
                '}';
    }

    public static class Builder {

        private Map<Language, PointOfInterestCategoryDetailsResource> details = new HashMap<>();

        public Builder withDetails(Language language, PointOfInterestCategoryDetailsResource detail) {
            details.put(language, detail);
            return this;
        }

        public NewPointOfInterestCategoryResource build() {
            return new NewPointOfInterestCategoryResource(this);
        }
    }
}
