package pl.kielce.tu.worldyouthday.pointofinterest.category.resource;

import pl.kielce.tu.worldyouthday.language.Language;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Łukasz Wesołowski on 10.04.2016.
 */

@XmlRootElement
public class PointOfInterestCategoryResource {
    private String id;
    private Map<Language, PointOfInterestCategoryDetailsResource> details;
    private Long version;

    public PointOfInterestCategoryResource(Builder builder) {
       id = builder.id;
       details = builder.details;
       version = builder.version;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getId() {
        return id;
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

        PointOfInterestCategoryResource that = (PointOfInterestCategoryResource) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (details != null ? !details.equals(that.details) : that.details != null) return false;
        return version != null ? version.equals(that.version) : that.version == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PointOfInterestCategoryResource{" +
                "id='" + id + '\'' +
                ", details=" + details +
                ", version=" + version +
                '}';
    }

    public static class Builder {
        private String id;
        private Map<Language, PointOfInterestCategoryDetailsResource> details = new HashMap<>();
        private Long version;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withDetail(Language language, PointOfInterestCategoryDetailsResource detail) {
            this.details.put(language, detail);
            return this;
        }

        public Builder withDetails(Map<Language, PointOfInterestCategoryDetailsResource> details) {
            this.details = details;
            return this;
        }

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public PointOfInterestCategoryResource build() {
            return new PointOfInterestCategoryResource(this);
        }
    }
}
