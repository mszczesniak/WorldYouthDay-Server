package pl.kielce.tu.worldyouthday.pointofinterest.resource;

import pl.kielce.tu.worldyouthday.language.Language;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vooka on 31.03.2016.
 */
public class PointOfInterestResource implements Serializable {
    private String id;
    private Double latitude;
    private Double longitude;
    private Long version;
    private Map<Language, PointOfInterestDetailsResource> details;
    private String categoryId;
    private String cityId;
    private List<String> imagesId;

    public PointOfInterestResource(Builder builder) {
        id = builder.id;
        latitude = builder.latitude;
        longitude = builder.longitude;
        version = builder.version;
        details = builder.details;
        categoryId = builder.categoryId;
        cityId = builder.cityId;
        imagesId = builder.imagesId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Long getVersion() {
        return version;
    }

    public Map<Language, PointOfInterestDetailsResource> getDetails() {
        return details;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCityId() {
        return cityId;
    }

    public List<String> getImagesId() {
        return imagesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointOfInterestResource that = (PointOfInterestResource) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (details != null ? !details.equals(that.details) : that.details != null) return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        return imagesId != null ? imagesId.equals(that.imagesId) : that.imagesId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (imagesId != null ? imagesId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PointOfInterestResource{" +
                "id='" + id + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", version=" + version +
                ", details=" + details +
                ", categoryId='" + categoryId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", imagesId=" + imagesId +
                '}';
    }

    public static class Builder {
        private String id;
        private Double latitude;
        private Double longitude;
        private Long version;
        private Map<Language, PointOfInterestDetailsResource> details = new HashMap<>();
        private String categoryId;
        private String cityId;
        private List<String> imagesId;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder withDetail(Language language, PointOfInterestDetailsResource details) {
            this.details.put(language, details);
            return this;
        }

        public Builder withDetails(Map<Language, PointOfInterestDetailsResource> details) {
            this.details = details;
            return this;
        }

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public Builder withCategoryId(String categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder withCityId(String cityId) {
            this.cityId = cityId;
            return this;
        }

        public Builder withImagesId(List<String> imagesId) {
            this.imagesId = imagesId;
            return this;
        }

        public PointOfInterestResource build() {
            return new PointOfInterestResource(this);
        }
    }
}
