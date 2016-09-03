package pl.kielce.tu.worldyouthday.pointofinterest.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.kielce.tu.worldyouthday.language.Language;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Łukasz Wesołowski on 10.04.2016.
 */
public class NewPointOfInterestResource implements Serializable {
    private Double latitude;
    private Double longitude;
    private String categoryId;
    private String cityId;
    private HashMap<Language, PointOfInterestDetailsResource> details;

    @JsonCreator
    public NewPointOfInterestResource(@JsonProperty("latitude") Double latitude,
                                      @JsonProperty("longitude") Double longitude,
                                      @JsonProperty("categoryId") String categoryId,
                                      @JsonProperty("cityId") String cityId,
                                      @JsonProperty("details") HashMap<Language, PointOfInterestDetailsResource> details) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.categoryId = categoryId;
        this.cityId = cityId;
        this.details = details;
    }

    public NewPointOfInterestResource(Builder builder) {
        latitude = builder.latitude;
        longitude = builder.longitude;
        categoryId = builder.categoryId;
        cityId = builder.cityId;
        details = builder.details;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCityId() {
        return cityId;
    }

    public HashMap<Language, PointOfInterestDetailsResource> getDetails() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewPointOfInterestResource that = (NewPointOfInterestResource) o;

        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        return details != null ? details.equals(that.details) : that.details == null;

    }

    @Override
    public int hashCode() {
        int result = latitude != null ? latitude.hashCode() : 0;
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewPointOfInterestResource{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", categoryId='" + categoryId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", details=" + details +
                '}';
    }

    public static class Builder {
        private Double latitude;
        private Double longitude;
        private String categoryId;
        private String cityId;
        private HashMap<Language, PointOfInterestDetailsResource> details = new HashMap<>();

        public Builder withLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withLongitude(Double longitude) {
            this.longitude = longitude;
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

        public Builder withDetails(Language language, PointOfInterestDetailsResource details) {
            this.details.put(language, details);
            return this;
        }

        public NewPointOfInterestResource build() {
            return new NewPointOfInterestResource(this);
        }
    }
}
