package pl.kielce.tu.worldyouthday.pointofinterest.resource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Łukasz Wesołowski on 17.05.2016.
 */
public class RemovePointOfInterestImageResource implements Serializable {
    private String pointOfInterestId;
    private String imageId;

    @JsonCreator
    public RemovePointOfInterestImageResource(@JsonProperty("pointOfInterestId") String pointOfInterestId,
                                              @JsonProperty("imageId") String imageId) {
        this.pointOfInterestId = pointOfInterestId;
        this.imageId = imageId;
    }

    public RemovePointOfInterestImageResource(Builder builder) {
        pointOfInterestId = builder.pointOfInterestId;
        imageId = builder.imageId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getPointOfInterestId() {
        return pointOfInterestId;
    }

    public String getImageId() {
        return imageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RemovePointOfInterestImageResource that = (RemovePointOfInterestImageResource) o;

        if (pointOfInterestId != null ? !pointOfInterestId.equals(that.pointOfInterestId) : that.pointOfInterestId != null)
            return false;
        return imageId != null ? imageId.equals(that.imageId) : that.imageId == null;

    }

    @Override
    public int hashCode() {
        int result = pointOfInterestId != null ? pointOfInterestId.hashCode() : 0;
        result = 31 * result + (imageId != null ? imageId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RemovePointOfInterestImageResource{" +
                "pointOfInterestId='" + pointOfInterestId + '\'' +
                ", imageId='" + imageId + '\'' +
                '}';
    }

    public static class Builder {
        private String pointOfInterestId;
        private String imageId;

        public Builder withPointOfInterestId(String pointOfInterestId) {
            this.pointOfInterestId = pointOfInterestId;
            return this;
        }

        public Builder withImageId(String imageId) {
            this.imageId = imageId;
            return this;
        }

        public RemovePointOfInterestImageResource build() {
            return new RemovePointOfInterestImageResource(this);
        }
    }
}
