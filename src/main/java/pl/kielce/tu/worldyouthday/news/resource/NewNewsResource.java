package pl.kielce.tu.worldyouthday.news.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.kielce.tu.worldyouthday.language.Language;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Łukasz Wesołowski on 28.04.2016.
 */
public class NewNewsResource implements Serializable {
    private String authorId;
    private String cityId;
    private Map<Language, NewsDetailsResource> details;

    @JsonCreator
    public NewNewsResource(@JsonProperty("authorId") String authorId,
                           @JsonProperty("cityId") String cityId,
                           @JsonProperty("details") Map<Language, NewsDetailsResource> details) {
        this.authorId = authorId;
        this.cityId = cityId;
        this.details = details;
    }

    public NewNewsResource(Builder builder) {
        authorId = builder.authorId;
        cityId = builder.cityId;
        details = builder.details;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewNewsResource that = (NewNewsResource) o;

        if (authorId != null ? !authorId.equals(that.authorId) : that.authorId != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        return details != null ? details.equals(that.details) : that.details == null;

    }

    @Override
    public int hashCode() {
        int result = authorId != null ? authorId.hashCode() : 0;
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewNewsResource{" +
                "authorId='" + authorId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", details=" + details +
                '}';
    }

    public static class Builder {
        private String authorId;
        private String cityId;
        private Map<Language, NewsDetailsResource> details = new HashMap<>();

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

        public NewNewsResource build() {
            return new NewNewsResource(this);
        }
    }
}
