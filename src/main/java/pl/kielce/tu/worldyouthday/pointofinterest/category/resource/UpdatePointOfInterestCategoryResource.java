package pl.kielce.tu.worldyouthday.pointofinterest.category.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.kielce.tu.worldyouthday.language.Language;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Łukasz Wesołowski on 13.04.2016.
 */

@XmlRootElement
public class UpdatePointOfInterestCategoryResource {

    private Map<Language, PointOfInterestCategoryDetailsResource> details;
    private Long version;

    @JsonCreator
    public UpdatePointOfInterestCategoryResource(@JsonProperty("details") Map<Language, PointOfInterestCategoryDetailsResource> details,
                                                 @JsonProperty("version") Long version) {
        this.details = details;
        this.version = version;
    }

    public UpdatePointOfInterestCategoryResource(Builder builder) {
        details = builder.details;
        version = builder.version;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Map<Language, PointOfInterestCategoryDetailsResource> getDetails() {
        return details;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdatePointOfInterestCategoryResource that = (UpdatePointOfInterestCategoryResource) o;

        if (details != null ? !details.equals(that.details) : that.details != null) return false;
        return !(version != null ? !version.equals(that.version) : that.version != null);

    }

    @Override
    public int hashCode() {
        int result = details != null ? details.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdatePointOfInterestCategoryResource{" +
                "details=" + details +
                ", version=" + version +
                '}';
    }

    public static class Builder {
        private Map<Language, PointOfInterestCategoryDetailsResource> details = new HashMap<>();
        private Long version;

        public Builder withDetails(Language language, PointOfInterestCategoryDetailsResource details) {
            this.details.put(language, details);
            return this;
        }

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public UpdatePointOfInterestCategoryResource build() {
            return new UpdatePointOfInterestCategoryResource(this);
        }
    }
}
