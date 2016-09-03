package pl.kielce.tu.worldyouthday.news.resource;

import pl.kielce.tu.worldyouthday.language.Language;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Łukasz Wesołowski on 01.05.2016.
 */
public class NewsResource implements Serializable {
    private String id;
    private String cityId;
    private Map<Language, NewsDetailsResource> details;
    private Date creationDate;
    private String creationAuthorId;
    private Date modificationDate;
    private String modificationAuthorId;
    private Long version;

    public NewsResource(Builder builder) {
        id = builder.id;
        cityId = builder.cityId;
        details = builder.details;
        creationDate = builder.creationDate;
        creationAuthorId = builder.creationAuthorId;
        modificationDate = builder.modificationDate;
        modificationAuthorId = builder.modificationAuthorId;
        version = builder.version;
    }

    public static Builder newBuilder() {
       return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getCityId() {
        return cityId;
    }

    public Map<Language, NewsDetailsResource> getDetails() {
        return details;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getCreationAuthorId() {
        return creationAuthorId;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public String getModificationAuthorId() {
        return modificationAuthorId;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsResource that = (NewsResource) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        if (details != null ? !details.equals(that.details) : that.details != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (creationAuthorId != null ? !creationAuthorId.equals(that.creationAuthorId) : that.creationAuthorId != null)
            return false;
        if (modificationDate != null ? !modificationDate.equals(that.modificationDate) : that.modificationDate != null)
            return false;
        if (modificationAuthorId != null ? !modificationAuthorId.equals(that.modificationAuthorId) : that.modificationAuthorId != null)
            return false;
        return version != null ? version.equals(that.version) : that.version == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (creationAuthorId != null ? creationAuthorId.hashCode() : 0);
        result = 31 * result + (modificationDate != null ? modificationDate.hashCode() : 0);
        result = 31 * result + (modificationAuthorId != null ? modificationAuthorId.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewsResource{" +
                "id='" + id + '\'' +
                ", cityId='" + cityId + '\'' +
                ", details=" + details +
                ", creationDate=" + creationDate +
                ", creationAuthorId='" + creationAuthorId + '\'' +
                ", modificationDate=" + modificationDate +
                ", modificationAuthorId='" + modificationAuthorId + '\'' +
                ", version=" + version +
                '}';
    }

    public static class Builder {
        private String id;
        private String cityId;
        private Map<Language, NewsDetailsResource> details = new HashMap<>();
        private Date creationDate;
        private String creationAuthorId;
        private Date modificationDate;
        private String modificationAuthorId;
        private Long version;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withCityId(String cityId) {
            this.cityId = cityId;
            return this;
        }

        public Builder withDetails(Language language, NewsDetailsResource detail) {
            this.details.put(language, detail);
            return this;
        }

        public Builder withDetails(Map<Language, NewsDetailsResource> details) {
            this.details = details;
            return this;
        }

        public Builder withCreationDate(Date creationDate) {
            this.creationDate = creationDate;
            return this;
        }

        public Builder withCreationAuthorId(String creationAuthorId) {
            this.creationAuthorId = creationAuthorId;
            return this;
        }

        public Builder withModificationDate(Date modificationDate) {
            this.modificationDate = modificationDate;
            return this;
        }

        public Builder withModificationAuthorId(String modificationAuthorId) {
            this.modificationAuthorId = modificationAuthorId;
            return this;
        }

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public NewsResource build() {
            return new NewsResource(this);
        }
    }
}
