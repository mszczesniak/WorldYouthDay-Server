package pl.kielce.tu.worldyouthday.news.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.kielce.tu.worldyouthday.language.Language;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Łukasz Wesołowski on 01.05.2016.
 */
public class UpdateNewsResource implements Serializable {
    private String authorId;
    private String cityId;
    private Map<Language, NewsDetailsResource> details;
    private Long version;

    @JsonCreator
    public UpdateNewsResource(@JsonProperty("creationAuthorId") String authorId,
                              @JsonProperty("cityId") String cityId,
                              @JsonProperty("details") Map<Language, NewsDetailsResource> details,
                              @JsonProperty("version") Long version) {
        this.authorId = authorId;
        this.cityId = cityId;
        this.details = details;
        this.version = version;
    }

    public UpdateNewsResource(Builder builder) {
        authorId = builder.authorId;
        cityId = builder.cityId;
        details = builder.details;
        version = builder.version;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getCityId() {
        return cityId;
    }

    public Map<Language, NewsDetailsResource> getDetails() {
        return details;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateNewsResource that = (UpdateNewsResource) o;

        if (authorId != null ? !authorId.equals(that.authorId) : that.authorId != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        if (details != null ? !details.equals(that.details) : that.details != null) return false;
        return version != null ? version.equals(that.version) : that.version == null;

    }

    @Override
    public int hashCode() {
        int result = authorId != null ? authorId.hashCode() : 0;
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdateNewsResource{" +
                "authorId='" + authorId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", details=" + details +
                ", version=" + version +
                '}';
    }

    public static class Builder {
        private String authorId;
        private String cityId;
        private Map<Language, NewsDetailsResource> details = new HashMap<>();
        private Long version;

        public Builder withAuthorId(String authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder withCityId(String cityId) {
            this.cityId = cityId;
            return this;
        }

        public Builder withDetails(Language language, NewsDetailsResource details) {
            this.details.put(language, details);
            return this;
        }

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public UpdateNewsResource build() {
            return new UpdateNewsResource(this);
        }
    }
}
